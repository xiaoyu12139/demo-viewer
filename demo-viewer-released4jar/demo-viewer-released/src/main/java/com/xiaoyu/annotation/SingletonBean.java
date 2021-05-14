package com.xiaoyu.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 标注要实例化为单例的类上面
 * @author ji.zhou
 *
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface SingletonBean {
	//注解类的实例化名称
	String value() default "";
}
