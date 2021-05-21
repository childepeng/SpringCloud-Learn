package cc.laop.security.constant;

/**
 * @Auther: peng
 * @Date: create in 2021/5/21 10:53
 * @Description:
 */
public enum ResultCodeEnum {


    SUCCESS(0, "登录成功"),

    USER_NOT_FOUND(1001, "用户不存在"),

    USER_EXPIRED(1002, "用户过期"),

    USER_LOCKED(1003, "用户被锁定"),

    USER_PASSWORD_WRONG(1004, "用户名或密码错误"),

    USER_DISABLED(1005, "用户被禁用"),

    USER_CREDENTIAL_EXPIRED(1006, "用户凭证过期"),

    USER_NO_PERMISSION(1007, "没有权限"),

    USER_NO_LOGIN(1008, "未登录"),

    UNKNOWN_ERROR(1099, "未知异常");


    ResultCodeEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }

    private int code;
    private String message;

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
