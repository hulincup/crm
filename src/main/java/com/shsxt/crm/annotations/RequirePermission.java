package com.shsxt.crm.annotations;

import java.lang.annotation.*;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RequirePermission {
    /**
     * 括号表示这个注解有参数
     * String code() = myAnnotation.code();
     * @return
     */
    String code() default "";
}
