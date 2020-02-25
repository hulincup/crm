package com.shsxt.crm;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.shsxt.crm.exceptions.NoLoginException;
import com.shsxt.crm.exceptions.ParamsException;
import com.shsxt.crm.model.ResultInfo;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
@Component
public class GlobalExceptionResolver implements HandlerExceptionResolver {
    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        /**
         * 首先判断异常类型
         *   如果异常类型为未登录异常  执行视图转发
         */
        ModelAndView modelAndView = new ModelAndView();
        if (ex instanceof NoLoginException){
            NoLoginException ne = (NoLoginException)ex;
            modelAndView.setViewName("no_login");
            modelAndView.addObject("msg",ne.getMsg());
            modelAndView.addObject("ctx",request.getContextPath());
            return modelAndView;
        }
        /**方法返回值类型判断:
         *    如果方法级别存在@ResponseBody 方法响应内容为json  否则视图
         *    handler 参数类型为HandlerMethod
         * 返回值
         *    视图:默认错误页面
         *
         *
         *
         *    json:错误的json信息
         */
        modelAndView.setViewName("errors");
        modelAndView.addObject("code",400);
        modelAndView.addObject("msg","系统异常请重试");
        if (handler instanceof HandlerMethod){
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            ResponseBody responseBody = handlerMethod.getMethod().getDeclaredAnnotation(ResponseBody.class);
            if (responseBody==null){
                /**
                 * 方法返回视图
                 */
                if (ex instanceof ParamsException){
                    ParamsException paramsException = (ParamsException) ex;
                    modelAndView.addObject("msg",paramsException.getMsg());
                    modelAndView.addObject("code",paramsException.getCode());

                }
                return modelAndView;
            } else {
                /**
                 * 方法返回json
                 */
                ResultInfo resultInfo = new ResultInfo();
                resultInfo.setCode(300);
                resultInfo.setMsg("系统错误请稍后再试");
                if (ex instanceof ParamsException){
                    ParamsException paramsException = (ParamsException) ex;
                    resultInfo.setMsg(paramsException.getMsg());
                    resultInfo.setCode(paramsException.getCode());

                }
                response.setContentType("application/json;charset=utf-8");
                response.setCharacterEncoding("utf-8");
                PrintWriter printWriter = null;
                try {
                    printWriter = response.getWriter();
                    printWriter.write(JSON.toJSONString(resultInfo));
                    printWriter.flush();
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (printWriter!=null){
                        printWriter.close();
                    }
                }
                return null;
            }
        }else {
            return  modelAndView;
        }
    }
}
