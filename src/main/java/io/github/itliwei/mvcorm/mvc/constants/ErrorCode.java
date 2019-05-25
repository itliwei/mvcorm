package io.github.itliwei.mvcorm.mvc.constants;

import java.io.Serializable;

public class ErrorCode implements Serializable {
    public static final String ERROR_CODE = "40000";
    public static final ErrorCode PARAM_REQUIRED = new ErrorCode("40001", "必填参数【%s】为空");
    public static final ErrorCode SIGN = new ErrorCode("40002", "签名验证失败");
    public static final ErrorCode AUTHORITY = new ErrorCode("40003", "无权限做此操作");
    public static final ErrorCode DECRYPT = new ErrorCode("40004", "解密失败");
    public static final ErrorCode INVALID_CALLER = new ErrorCode("40005", "非法调用方");
    public static final ErrorCode PARAM_INVALID = new ErrorCode("40006", "参数格式无效【%s】");
    public static final ErrorCode UPLOAD_TYPE = new ErrorCode("40007", "文件类型只允许上传【%s】");
    public static final ErrorCode UPLOAD_SIZE = new ErrorCode("40008", "文件大小不可超过【%s】M");
    public static final ErrorCode DAO_ERROR = new ErrorCode("40009", "数据库异常:【%s】");
    public static final ErrorCode DATA_EXIST = new ErrorCode("40016", "【%s】已经存在");
    public static final ErrorCode DATA_NOT_EXIST = new ErrorCode("40017", "【%s】不存在");
    public static final ErrorCode PARAMS_VALIDA_ERROR = new ErrorCode("40018", "参数校验异常：【%s】");
    public static final ErrorCode REQUEST_THIRD = new ErrorCode("40021", "请求外系统异常：【%s】");
    public static final ErrorCode SERVER = new ErrorCode("50000", "服务器异常【%s】");
    private String code;
    private String message;

    public ErrorCode() {
        throw new RuntimeException("不能使用");
    }

    public ErrorCode(String message) {
        this.code = "40000";
        this.message = message;
    }

    public ErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return this.code;
    }

    public String getMessage() {
        return this.message;
    }

    public static ErrorCode ofError(String message) {
        return new ErrorCode("40000", message);
    }
}