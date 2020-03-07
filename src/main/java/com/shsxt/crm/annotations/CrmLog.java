package com.shsxt.crm.annotations;


import java.lang.annotation.*;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface CrmLog {
    String oper() default "";
    String module() default "";
}
