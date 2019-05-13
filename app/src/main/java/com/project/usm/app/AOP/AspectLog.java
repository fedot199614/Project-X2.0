package com.project.usm.app.AOP;

import android.util.Log;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.reflect.Method;


@Aspect
public class AspectLog {

    private static final String TAG = "LOG_FULL";
    private static final String TAG2 = "LOG_FOCUSED";
    @Before("execution(* *(..))")
    public void doBeforeAyMethod(JoinPoint joinPoint) {
        if(joinPoint.getTarget() != null) {
            MethodSignature signature = (MethodSignature) joinPoint.getSignature();
            Method method = signature.getMethod();
            if(method!= null) {
                Log.i(TAG, joinPoint.getTarget().getClass().getSimpleName() + " : " + method.getName());
            }
        }
    }

    @After("@annotation(com.project.usm.app.AOP.Annotations.Loggable)")
    public  void test(JoinPoint joinPoint){
            if(joinPoint.getSignature() != null) {
                Log.e(TAG2,  joinPoint.getSignature().getDeclaringType().getSimpleName() + " : " + joinPoint.getSignature().getName());
            }

    }

}