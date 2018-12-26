package com.dinpay.demo.base.dagger2;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;


/**
 *
 * @Scope
 *
 * 自定义注解限定注解的作用域
 *我们在Activity里注入两个Object对象，并且要保证这两个对象是同一个对象。
 * 首先肯定是不能用@Singleton注解的，因为@Singleton是全局单例。
 * 这是我们用自定义的@Scope注解实现局部单例。
 *
 * 首先自定义一个接口，使用@Scope注解，目的是为Activity提供局部单例
 * @author yzh-t105
 */
@Scope
@Retention(RetentionPolicy.RUNTIME)
public @interface ActivityScope {
}
