package com.frankmoley.lil.fid.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@Aspect
public class CountingAspect {
    private static final Logger LOGGER = LoggerFactory.getLogger("Method Call Counter");

    private static final Map<String, Integer> countMap = new HashMap<>();

    @Pointcut("@annotation(Countable)")
    public void executeCounting(){}

    @Before("executeCounting()")
    public void countMethodCall(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getDeclaringType() + "." + joinPoint.getSignature().getName();
        if (countMap.containsKey(methodName)) {
            int curr = countMap.get(methodName);
            curr++;
            countMap.put(methodName, curr);
        }
        else {
            countMap.put(methodName, 1);
        }

        StringBuilder message  = new StringBuilder("Current Counts Are ... |");
        countMap.forEach((k,v) -> {
            message.append(k).append("::").append(v).append(" | ");
        });
        LOGGER.info(message.toString());
    }

}
