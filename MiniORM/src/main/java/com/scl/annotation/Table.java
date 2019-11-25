package com.scl.annotation;

import java.lang.annotation.*;

/**
 * Created with IntelliJ IDEA.
 * User: chenglu
 * Date: 2019/11/23
 * Description: 表示表名
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Table {
  String name() default "";
}
