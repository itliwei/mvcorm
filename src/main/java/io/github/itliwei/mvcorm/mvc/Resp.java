package io.github.itliwei.mvcorm.mvc;

import com.google.common.collect.Maps;
import io.github.itliwei.mvcorm.mvc.constants.ErrorCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Map;

@Getter
@Setter
public class Resp<T> implements Serializable {
    private static final String CODE_SUCCESS = "20000";
    private static final String MESSAGE_SUCCESS = "success";
    private String code;
    private String message;
    private Long timestamp;
    private T data;

    private Resp(String code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
        this.timestamp = System.currentTimeMillis();
    }

    public boolean isSuccess() {
        return "20000".equals(this.getCode());
    }

    public static Resp success(Object data) {
        return new Resp("20000", "success", data);
    }

    public static Resp success(String key, Object value) {
        Map<String, Object> data = Maps.newHashMap();
        data.put(key, value);
        return new Resp("20000", "success", data);
    }

    public static Resp successWithMsg(String msg) {
        return new Resp("20000", msg, "success");
    }

    public static Resp success() {
        return new Resp("20000", "success", "success");
    }

    public static Resp error(ErrorCode errorCode, String... message) {
        return new Resp(errorCode.getCode(), String.format(errorCode.getMessage(), message), "error");
    }

    public static Resp error(String code, String message) {
        return new Resp(code, message, "error");
    }

    public static Resp error(ErrorCode errorCode, Map<String, Object> data) {
        return new Resp(errorCode.getCode(), errorCode.getMessage(), data);
    }

    public static Resp error(ErrorCode code) {
        return new Resp(code.getCode(), code.getMessage(), "error");
    }


    public String toString() {
        StringBuilder sb = new StringBuilder("Resp{");
        sb.append("code='").append(this.code).append('\'');
        sb.append(", message='").append(this.message).append('\'');
        sb.append(", timestamp='").append(this.timestamp).append('\'');
        sb.append(", data=").append(this.data);
        sb.append('}');
        return sb.toString();
    }
}
