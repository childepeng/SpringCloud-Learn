package cc.laop.gateway.dto;

/**
 * @Auther: peng
 * @Date: create in 2021/5/12 18:20
 * @Description:
 */
public enum ResultCodeEnum {

    SUCCESS(0, "Success"),

    ILLEGAL_ARGUMENT(10001, "参数异常"),

    SIGN_EXPIRED(10011, "签名过期"),

    SIGN_CHECK_FAILED(10012, "签名验证失败"),

    TOKEN_CHECK_FAILED(10021, "token验证失败"),

    TOKEN_ILLEGAL(10021, "TOKEN不合法"),

    TOKEN_EXPIRED(10022, "TOKEN过期");

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
