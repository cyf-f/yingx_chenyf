package com.baizhi.cyf.aspect;

import com.baizhi.cyf.annotation.AddLog;
import com.baizhi.cyf.entity.Admin;
import com.baizhi.cyf.entity.Log;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.UUID;

@Aspect
@Configuration
public class LogAspect {

    @Resource
    HttpServletRequest request;

    //环绕通知
    @Around("@annotation(com.baizhi.cyf.annotation.AddLog)") //注解的全限定名
    public Object addLogs(ProceedingJoinPoint proceedingJoinPoint){

        //获得切到的方法
        MethodSignature signature = (MethodSignature) proceedingJoinPoint.getSignature();
        //获取方法信息
        Method method = signature.getMethod();

        //获取方法上的注解   哪个注解
        AddLog addLog = method.getAnnotation(AddLog.class);
        //获取注解中的值    value
        String value = addLog.value();

        //谁   时间   操作   是否成功
        Admin admin = (Admin)request.getSession().getAttribute("admin");
        //时间
        Date date = new Date();
        //操作
        String methodName = proceedingJoinPoint.getSignature().getName();
        try {
            //放行方法
            Object proceed = proceedingJoinPoint.proceed();
            String message="success";
            //日志信息入库
            System.out.println("管理员："+admin.getUsername()+"--时间："+date+"--方法："+value+"("+methodName+")"+"--是否成功:"+message);
            Log log = new Log(UUID.randomUUID().toString(),admin.getUsername(),date,value+"("+methodName+")",message);
            System.out.println(log);

            return proceed;
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            String message="error";
            return null;
        }
    }

    //环绕通知
    //@Around("execution(* com.baizhi.serviceImpl.*.*(..)) && !execution(* com.baizhi.serviceImpl.*.query*(..)) ")
    public Object addLog(ProceedingJoinPoint proceedingJoinPoint){

        //谁   时间   操作   是否成功
        Admin admin = (Admin)request.getSession().getAttribute("admin");
        //时间
        Date date = new Date();
        //操作
        String methodName = proceedingJoinPoint.getSignature().getName();
        try {
            //放行方法
            Object proceed = proceedingJoinPoint.proceed();
            String message="success";
            System.out.println(admin.getUsername()+"--"+date+"--"+methodName+"--"+message);
            return proceed;
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            String message="error";
            return null;
        }
    }
}

















