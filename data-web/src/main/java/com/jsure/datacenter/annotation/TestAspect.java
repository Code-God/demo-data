package com.jsure.datacenter.annotation;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * @Author: wuxiaobiao
 * @Description:
 * @Date: Created in 2018/6/4
 * @Time: 17:56
 * I am a Code Man -_-!
 */
@Aspect
@Component
public class TestAspect {

    @Pointcut("@annotation(TestAnnotation)")
    private void pointCut(){
    }

    @Before("pointCut()")
    public void BeforeCall()
    {
        System.out.println("事前通知");
    }

    @Around("pointCut()")
    public Object AroundCall(ProceedingJoinPoint joinPoint)
    {
        Object result = null;
        System.out.println("环绕通知之开始");
        try {
            result = joinPoint.proceed();
            MethodSignature methodSignature = (MethodSignature)joinPoint.getSignature();
            Method method = methodSignature.getMethod();
            TestAnnotation annotation = method.getAnnotation(TestAnnotation.class);
            System.out.println("注解参数 : " + annotation.desc());
        } catch (Throwable e) {
            e.printStackTrace();
        }
        System.out.println("环绕通知之结束");
        return result;
    }

//    @After("pointCut()")
    @AfterReturning(returning="rvt", pointcut="pointCut()")
    public void AfterCall(JoinPoint joinPoint, Object rvt) {
        //pointcut是对应的注解类   rvt就是方法运行完之后要返回的值
        System.out.println("AfterReturning增强：获取目标方法的返回值：" + rvt);
        System.out.println("事后通知");
    }


}
