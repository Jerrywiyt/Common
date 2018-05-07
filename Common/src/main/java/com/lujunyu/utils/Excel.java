package com.lujunyu.utils;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Excel注解，用以生成Excel表格文件
 * 可以扩展字体大小颜色、以及格式化的函数等。
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD,ElementType.TYPE})
public @interface Excel {
    //列名
    String name() default "";

    //宽度
    int width() default 20;

    //忽略该字段
    boolean skip() default false;
}
