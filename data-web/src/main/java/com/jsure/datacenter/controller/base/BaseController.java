package com.jsure.datacenter.controller.base;

import com.google.common.collect.Maps;
import com.jsure.datacenter.constant.CustomConstant;
import com.jsure.datacenter.exception.CustomException;
import com.jsure.datacenter.model.enums.CustomErrorEnum;
import com.jsure.datacenter.utils.Response;
import com.jsure.datacenter.utils.ObjectUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: wuxiaobiao
 * @Description: 基类 Controller
 * @Date: Created in 2018/5/21
 * @Time: 16:02
 * I am a Code Man -_-!
 */
@Slf4j
@Controller
public class BaseController {

    @Autowired
    protected HttpServletRequest request;

    @Autowired
    protected HttpServletResponse response;

    @Autowired
    protected HttpSession session;

    /**
     * 参数失败返回信息
     * @return
     */
    protected Map<String, Object> paramFailedMessage() {
        Map<String, Object> map = Maps.newHashMap();
        map.put("code", "1");
        map.put("msg", "参数异常");
        map.put("data", null);
        return map;
    }

    /**
     * 成功返回数据
     * @param r
     * @param msg
     * @param data
     * @return
     */
    protected Response successResult(Response r, String msg, Map<String, Object> data) {
        r.setResCode(CustomErrorEnum.SUCCESS_CODE_341000.getErrorCode());
        r.setResMsg(msg);
        r.setResult(data);
        return r;
    }

    /**
     * 返回失败数据
     * @param r
     * @param code
     * @param msg
     * @param data
     * @return
     */
    protected Response failedResult(Response r, String code, String msg, Map<String, Object> data) {
        r.setResCode(code);
        r.setResMsg(msg);
        r.setResult(data);
        return r;
    }

    /**
     * 返回失败数据（程序异常）
     * @param r
     * @param data
     * @return
     */
    protected Response failedResult(Response r, Map<String, Object> data) {
        r.setResCode(CustomErrorEnum.ERROR_CODE_341FFF.getErrorCode());
        r.setResMsg(CustomErrorEnum.ERROR_CODE_341FFF.getErrorDesc());
        r.setResult(data);
        return r;
    }

    /**
     * <功能详细描述> 成功返回数据 (Map形式)
     *
     * @param data
     * @return
     * @see [类、类#方法、类#成员]
     */
    protected Map<String, Object> successData(String msg, Map<String, Object> data) {
        Map<String, Object> map = Maps.newHashMap();
        map.put("resCode", CustomErrorEnum.SUCCESS_CODE_341000.getErrorCode());
        map.put("resMsg", msg);
        if(ObjectUtils.isNotNullAndEmpty(data)){
            map.put("result", data);
        }
        return map;
    }

    /**
     * <功能详细描述> 返回失败数据 (Map形式)
     *
     * @param data
     * @return
     * @see [类、类#方法、类#成员]
     */
    protected Map<String, Object> failedData(String code, String msg, Map<String, Object> data) {
        Map<String, Object> map = Maps.newHashMap();
        map.put("resCode", code);
        map.put("resMsg", msg);
        if(ObjectUtils.isNotNullAndEmpty(data)){
            map.put("result", data);
        }
        return map;
    }

    /**
     * 将request的参数转换成字符串
     *
     * @param request
     * @return
     */
    protected String requestParamToString(HttpServletRequest request) {
        Map<String, String> paramMap = toStringParam(request.getParameterMap());
        Map<String, String> pathVarMap = toStringPathVar(request);
        StringBuffer sb = new StringBuffer();
        //拼接param字符串
        if (paramMap != null) {
            sb.append("REQUEST.PARAM").append(paramMap);
        }
        //拼接pathVariable字符串
        if (pathVarMap != null) {
            sb.append("REQUEST.PATH_VARIABLE").append(pathVarMap);
        }
        return sb.toString();
    }

    /**
     * 获取request的param参数对应的map
     *
     * @param map
     * @return
     */
    private Map<String, String> toStringParam(Map map) {
        Map<String, String> paramMap = null;
        for (Object key : map.keySet()) {
            if (key == null) {
                continue;
            }
            if (paramMap == null) {
                paramMap = new HashMap<>();
            }
            String tempVal = request.getParameter(key.toString()) != null ? request.getParameter(key.toString()) : null;
            //保密属性脱敏
            if (CustomConstant.INSENSITIVE_LIST.contains(key.toString().toLowerCase())) {
                tempVal = CustomConstant.INSENSITIVE_STRING;
            }
            paramMap.put(key.toString(), tempVal);
        }
        return paramMap;
    }

//    /**
//     * 获取body的json数据
//     * @param request
//     * @return
//     */
//    protected Map<String, String> toJsonStringParam(HttpServletRequest request) {
//        Map<String, Object> map = null;
//        try {
//
////            // 获取输入流
////            BufferedReader streamReader = new BufferedReader(new InputStreamReader(request.getInputStream(), "UTF-8"));
////            // 写入数据到Stringbuilder
////            StringBuilder sb = new StringBuilder();
////            String line = null;
////            while ((line = streamReader.readLine()) != null) {
////                sb.append(line);
////            }
//            InputStream stream = request.getInputStream();
////            InputStreamReader streamReader = new InputStreamReader(stream,"UTF-8");
////            BufferedReader reader = new BufferedReader(streamReader);
//            String s = IOUtils.toString(stream, "utf-8");
////            StringBuilder sb = new StringBuilder();
////            char[] buf = new char[1024];
////            int rd;
////            while ((rd = reader.read(buf)) != -1) {
////                sb.append(buf, 0, rd);
////            }
////            map = JSONUtil.json2map(sb.toString());
//        }catch (IOException e) {
//            e.printStackTrace();
//        }
//        return toStringParam(map);
//    }


    /**
     * 获取pathVariable注解的参数map
     *
     * @param request
     * @return
     */
    private Map toStringPathVar(HttpServletRequest request) {
        Map pathVariableMap = request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE) != null ? (Map) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE) : null;
        if (ObjectUtils.isNullOrEmpty(pathVariableMap)) {
            pathVariableMap = null;
        }
        return pathVariableMap;
    }

    /**
     * 检查是否有访问当前URL的权限
     * @param strs
     */
    protected void checkShiroPermission(String... strs) {
        if (!hasPermission(strs)) {
            throw new CustomException(CustomErrorEnum.ERROR_CODE_341008.getErrorCode(), CustomErrorEnum.ERROR_CODE_341008.getErrorDesc());
        }
    }

    /**
     * 校验权限
     *
     * @param strs 数组
     */
    protected boolean hasPermission(String... strs) {
        boolean result = true;
        Subject subject = SecurityUtils.getSubject();
        for (String str : strs) {
            if (!subject.isPermitted(str)) {
                result = false;
            }
        }
        return result;
    }

    /**
     * 校验权限
     *
     * @param strs 数组
     */
    protected void checkPermission(String... strs) {
        Subject subject = SecurityUtils.getSubject();
        for (String str : strs) {
            subject.checkPermission(str);
        }
    }
}
