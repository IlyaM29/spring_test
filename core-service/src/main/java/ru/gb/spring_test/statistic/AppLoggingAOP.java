package ru.gb.spring_test.statistic;

import lombok.Data;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Data
public class AppLoggingAOP {

    public Statistic statistic = new Statistic();

    @Around("execution(public * ru.gb.spring_test.services.*.*(..))")
    public Object durationServices(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        long begin = System.currentTimeMillis();
        Object out = proceedingJoinPoint.proceed();
        long end = System.currentTimeMillis();
        long duration = end - begin;
        System.out.println((MethodSignature) proceedingJoinPoint.getSignature() + " duration: " + duration);
        updateStatistic(proceedingJoinPoint.getTarget().getClass().getSimpleName(), duration);
        return out;
    }

    private void updateStatistic(String nameService, long time) {
        switch (nameService) {
            case "CartService": statistic.setCartTime(statistic.getCartTime() + time);
            case "OrderService": statistic.setOrderTime(statistic.getOrderTime() + time);
            case "ProductService": statistic.setProductTime(statistic.getProductTime() + time);
            case "UserService": statistic.setUserTime(statistic.getUserTime() + time);
        }
    }

}
