package io.github.itliwei.mvcorm.mvc;

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
 * @date 2019/05/14 9:38
 * @description api调用输出日志
 */
@Aspect
@Component
public class ApiLogAspect {

    private static final Logger LOGGER = LoggerFactory.getLogger(ApiLogAspect.class);

    @Pointcut("@annotation(org.springframework.web.bind.annotation.RestController)")
    public void addLogAspect() {
    }

    @Around("addLogAspect()")
    public Object addLog(ProceedingJoinPoint joinPoint) throws Throwable {
        Long begin = System.currentTimeMillis();
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("API-INVOKE").append(" | ");
        //添加URI
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        stringBuffer.append("URL:").append(request.getRequestURI()).append(" | ");
        stringBuffer.append("METHOD:").append(request.getMethod()).append(" | ");
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
            return result;
        } catch (Exception e) {
            stringBuffer.append("ERROR:").append(e.getMessage()).append(" | ");
            e.printStackTrace();
            throw e;
        } finally {
            //添加endTime
            stringBuffer.append("TIME:").append(System.currentTimeMillis() - begin);
            LOGGER.info(stringBuffer.toString());
        }
    }
}
