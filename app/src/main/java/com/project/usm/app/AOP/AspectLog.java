package com.project.usm.app.AOP;

import android.util.Log;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.reflect.Method;


@Aspect
public class AspectLog {


    private static final String TAG = "aspectLOG";

   //@Before("execution(* *(..)) && @within(Loggable)")
   // public void doBeforeAyMethod(JoinPoint joinPoint) {
        //if(joinPoint.getTarget() != null) {
          //  MethodSignature signature = (MethodSignature) joinPoint.getSignature();
          //  Method method = signature.getMethod();
          //  Log.i(TAG, joinPoint.getTarget().getClass().getSimpleName() + " : " + method.getName());
       // }
   // }


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

    //@Before("@annotation(Loggable)")
    // public  void test(){
       // Log.i("Проверка", "Подключение");
    //}

}