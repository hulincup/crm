package com.shsxt.crm.utils;

import com.github.pagehelper.PageException;
import com.shsxt.crm.exceptions.ParamsException;

public class AssertUtil {
    public static void isTrue(boolean flag,String msg){
        if (flag){
            throw new ParamsException(msg);
        }
    }
}
