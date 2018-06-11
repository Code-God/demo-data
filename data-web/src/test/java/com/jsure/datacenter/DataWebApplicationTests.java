package com.jsure.datacenter;

import com.jsure.datacenter.model.param.UserParam;
import com.jsure.datacenter.model.result.TUserResult;
import com.jsure.datacenter.service.userservice.TokenService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DataWebApplicationTests {

	@Autowired
	private TokenService tokenService;

	@Test
	public void contextLoads() {
	}

	@Test
	public void test1(){
		UserParam user = new UserParam();
		List<TUserResult> list = tokenService.findUserList(user);
		System.out.println(list);
	}
}
