package io.github.itliwei.mvcorm.mvc.Exception;

import com.google.common.base.MoreObjects;
import io.github.itliwei.mvcorm.mvc.constants.ErrorCode;

public class BusinessException extends RuntimeException {
    private String code;

    public BusinessException() {
    }

    public BusinessException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.code = errorCode.getCode();
    }

    public BusinessException(ErrorCode errorCode, Throwable cause) {
        super(errorCode.getMessage(), cause);
        this.code = errorCode.getCode();
    }

    public BusinessException(ErrorCode errorCode, String appendMessage) {
        super(String.format(errorCode.getMessage(), appendMessage == null ? "" : appendMessage));
        this.code = errorCode.getCode();
    }

    public BusinessException(String errorCode, String appendMessage) {
        super(appendMessage);
        this.code = errorCode;
    }

    public BusinessException(ErrorCode errorCode, String appendMessage, Throwable cause) {
        super(String.format(errorCode.getMessage(), appendMessage == null ? "" : appendMessage), cause);
        this.code = errorCode.getCode();
    }

    public String getCode() {
        return this.code;
    }

    public String toString() {
        return MoreObjects.toStringHelper(this).add("code", this.code).add("message", super.getMessage()).toString();
    }
}