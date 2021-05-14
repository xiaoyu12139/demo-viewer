package com.xiaoyu.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 标注在类上面，配置扫描注解的基础包
 * @author ji.zhou
 *
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ApplicationBoot {
	//注解类的路径
	String value() default "";
}
