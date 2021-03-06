package br.com.jcaguiar.cinephiles.util;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.stream.Collectors;

@Aspect
@Component
public class ConsoleLogAspect {

    @Around("@annotation(br.com.jcaguiar.cinephiles.util.ConsoleLog)")
    public Object printConsoleLogBefore(ProceedingJoinPoint joinPoint) throws Throwable {
        final MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        final String className = getMethodSimpleName(signature);
        final String methodName = signature.getMethod().getName();
        //Parameters Type
        final String methodArgTypes = Arrays.stream(signature.getParameterTypes())
            .map(type -> type != null ? type.getSimpleName() : "NULL")
            .collect(Collectors.joining(", "));
        //Parameters Value
        final String methodArgValues = Arrays.stream(joinPoint.getArgs())
            .map(obj -> obj != null ? obj.toString() : "NULL")
            .collect(Collectors.joining(", "));
        //Result
        System.out.printf("[CONSOLE-LOG] %s::%s(%s) = (%s) \n",
            className, methodName, methodArgTypes,methodArgValues);
        return joinPoint.proceed();
    }

    @AfterThrowing(value = "@annotation(br.com.jcaguiar.cinephiles.util.ConsoleLog)", throwing = "exception")
    public void printConsoleLogException(JoinPoint joinPoint, Exception exception) {
        final MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        final String className = getMethodSimpleName(signature);
        System.out.printf("[CONSOLE-LOG] %s::%s! --> %s \n",
            className,
            exception.getClass().getSimpleName(),
            exception.getMessage());
    }

    private static String getMethodSimpleName(MethodSignature signature)
    {
        final String[] className = signature.getDeclaringType().toString().split("\\.");
        return className[(className.length - 1)];
    }

}
