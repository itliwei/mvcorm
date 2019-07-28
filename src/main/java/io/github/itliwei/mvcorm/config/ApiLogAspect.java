package io.github.itliwei.mvcorm.config;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @author liwei
 * @date 2019/07/28 9:38
 * @description api调用输出日志
 */
@Aspect
@Component
@Slf4j
public class ApiLogAspect {


    @Pointcut("@annotation(org.springframework.web.bind.annotation.RequestMapping) " +
            "|| @annotation(org.springframework.web.bind.annotation.PostMapping)" +
            "|| @annotation(org.springframework.web.bind.annotation.GetMapping)" +
            "|| @annotation(org.springframework.web.bind.annotation.PutMapping)")
    public void addLogAspect() {
    }

    @Around("addLogAspect()")
    public Object addLog(ProceedingJoinPoint joinPoint) throws Throwable {
        Long begin = System.currentTimeMillis();
        StringBuffer stringBuffer = new StringBuffer();
        //添加URI
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        stringBuffer.append("URL:").append(request.getRequestURI()).append(" | ");
        stringBuffer.append("PARAMS:");
        try {
            if ("ERROR".equalsIgnoreCase(request.getDispatcherType().name())) {
                stringBuffer.append("ILLEGAL");
            } else {
                Object[] args = joinPoint.getArgs();
                for (Object arg : args) {
                    stringBuffer.append(arg).append(",");
                }
            }
        } catch (Exception e) {
            stringBuffer.append(e.getMessage());
        }
        stringBuffer.append(" | ");
        Object result = null;
        try {
            result = joinPoint.proceed();
            stringBuffer.append("RESULT:").append(result).append(" | ");
            return result;
        } catch (Exception e) {
            stringBuffer.append("ERROR:").append(e.getMessage()).append(" | ");
            e.printStackTrace();
            throw e;
        } finally {
            //添加endTime
            stringBuffer.append("TIME:").append(System.currentTimeMillis() - begin);
            log.warn(stringBuffer.toString());
        }
    }
}
