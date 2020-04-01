package com.lei.mywechat.utils.datasource;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE,ElementType.METHOD})
@Documented
public @interface DS {
    String value() default "dataSource1";
}
