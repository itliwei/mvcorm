package io.github.itliwei.mvcorm.config;

import com.alibaba.druid.support.json.JSONUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdvice;

import java.io.IOException;
import java.lang.reflect.Type;

/**
 * Description: 统一返回值日志打印
 *
 * @author liwei
 */
@Slf4j
@RestControllerAdvice
public class RequestAopLogComponent implements RequestBodyAdvice {

    @Override
    public boolean supports(MethodParameter methodParameter, Type targetType,
                            Class<? extends HttpMessageConverter<?>> converterType) {
       return true;
    }

    @Override
    public HttpInputMessage beforeBodyRead(HttpInputMessage inputMessage, MethodParameter parameter, Type targetType,
                                           Class<? extends HttpMessageConverter<?>> converterType) throws IOException {
        return inputMessage;
    }

    @Override
    public Object afterBodyRead(Object body, HttpInputMessage inputMessage, MethodParameter parameter,
                                Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
        RequestMapping requestMapping = parameter.getMethodAnnotation(RequestMapping.class);
        log.debug("requet url:{},param:{}", StringUtils.arrayToDelimitedString(requestMapping.value(), ","),
                JSONUtils.toJSONString(body));
        return body;
    }

    @Override
    public Object handleEmptyBody(Object body, HttpInputMessage inputMessage, MethodParameter parameter,
                                  Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
        RequestMapping requestMapping = parameter.getMethodAnnotation(RequestMapping.class);
        log.debug("requet url:{}", StringUtils.arrayToDelimitedString(requestMapping.value(), ","));
        return body;
    }
}
