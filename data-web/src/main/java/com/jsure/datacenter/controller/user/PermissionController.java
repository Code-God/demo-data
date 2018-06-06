package com.jsure.datacenter.controller.user;

import com.google.common.base.Throwables;
import com.google.common.collect.Maps;
import com.jsure.datacenter.model.enums.CustomErrorEnum;
import com.jsure.datacenter.exception.CustomException;
import com.jsure.datacenter.utils.JSONUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * @Author: wuxiaobiao
 * @Description:
 * @Date: Created in 2018/4/10
 * @Time: 11:39
 * I am a Code Man -_-!
 */
@Slf4j
@RestController
@RequestMapping("api/permission/")
@Api(tags="权限接口",description = "测试")
public class PermissionController {

    @RequestMapping(value = "userpermission", method = RequestMethod.POST)
    @ApiOperation(value = "获取用户权限列表", notes = "获取用户权限")
    public String userPermission(HttpSession session) {
        Map<String, Object> resultMap = Maps.newHashMap();
        try {
            //从session获取用户权限信息
            String[] permission = (String[]) session.getAttribute("permission");
            resultMap.put("permission", permission);
        } catch (CustomException jex) {
            resultMap.put("errorCode", jex.getCode());
            resultMap.put("errorMsg", jex.getMessage());
            log.error("failed to login, RESULT:{},cause:{}", resultMap, jex);
        } catch (Exception e) {
            resultMap.put("errorCode", CustomErrorEnum.ERROR_CODE_341FFF.getErrorCode());
            resultMap.put("errorMsg", CustomErrorEnum.ERROR_CODE_341FFF.getErrorDesc());
            log.error("failed to login,RESULT:{}, cause:{}", resultMap, Throwables.getStackTraceAsString(e));
        }
        return JSONUtil.obj2json(resultMap);

    }
}
