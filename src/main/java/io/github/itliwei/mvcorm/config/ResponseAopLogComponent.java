package io.github.itliwei.mvcorm.config;

import com.alibaba.druid.support.json.JSONUtils;
import io.github.itliwei.mvcorm.mvc.Resp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * Description: 统一返回值日志打印
 *
 * @author liwei
 */
@Slf4j
@RestControllerAdvice
public class ResponseAopLogComponent implements ResponseBodyAdvice {

    @Override
    public boolean supports(MethodParameter returnType, Class converterType) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType,
                                  Class selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {

        if (body instanceof Resp) {
            log.debug("result:{}", JSONUtils.toJSONString(body));
        }
        return body;
    }
}
