package com.shsxt.crm.proxy;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.google.gson.Gson;
import com.shsxt.crm.annotations.CrmLog;
import com.shsxt.crm.dao.LogMapper;
import com.shsxt.crm.service.UserService;
import com.shsxt.crm.utils.LoginUserUtil;
import com.shsxt.crm.vo.LogWithBLOBs;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Component
@Aspect
public class LogProxy {
    @Autowired
    private LogMapper logMapper;

    @Autowired
    private UserService userService;

    @Autowired
    private HttpServletRequest request;

    @Around(value = "@annotation(com.shsxt.crm.annotations.CrmLog)")
    public Object around(ProceedingJoinPoint proceedingJoinPoint) throws Throwable{
        Object result=null;
        LogWithBLOBs logWithBLOBs=new LogWithBLOBs();
        logWithBLOBs.setCreateDate(new Date());
        logWithBLOBs.setCreateMan(userService.selectByPrimaryKey(LoginUserUtil.releaseUserIdFromCookie(request)).getTrueName());
        MethodSignature methodSignature= (MethodSignature) proceedingJoinPoint.getSignature();
        CrmLog crmLog = methodSignature.getMethod().getDeclaredAnnotation(CrmLog.class);
        logWithBLOBs.setDescription(crmLog.module()+"-"+crmLog.oper());
        logWithBLOBs.setExceptionCode("200");
        logWithBLOBs.setExceptionDetail("操作成功");
        System.out.println(methodSignature.toString());
        logWithBLOBs.setMethod(methodSignature.toString());
        Long start=System.currentTimeMillis();
        result=proceedingJoinPoint.proceed();
        Long end=System.currentTimeMillis();
        logWithBLOBs.setExecuteTime((int)(end-start));
        logWithBLOBs.setType("1");
        logWithBLOBs.setRequestIp(request.getRemoteAddr());
        if (proceedingJoinPoint.getArgs()!=null){
            logWithBLOBs.setParams(new Gson().toJson(proceedingJoinPoint.getArgs()));
        }
        logWithBLOBs.setResult(JSON.toJSONString(result));
        logMapper.insertSelective(logWithBLOBs);
        return result;
    }
}
