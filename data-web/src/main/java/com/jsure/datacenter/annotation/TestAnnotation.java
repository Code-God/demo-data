package com.jsure.datacenter.annotation;

import java.lang.annotation.*;

/**
 * @Author: wuxiaobiao
 * @Description:
 * @Date: Created in 2018/6/4
 * @Time: 17:55
 * I am a Code Man -_-!
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface TestAnnotation {
    String desc() default "Hi Call";
}
