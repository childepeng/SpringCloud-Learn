package cc.laop.gateway.dto;

/**
 * @Auther: peng
 * @Date: create in 2021/5/12 18:20
 * @Description:
 */
public enum ResultCodEnum {

    ILLEGAL_ARGUMENT(10001, "参数异常"),

    SIGN_EXPIRED(10011, "签名过期"),

    SIGN_CHECK_FAILED(10012, "签名验证失败"),

    TOKEN_CHECK_FAILED(10021, "token验证失败");

    ResultCodEnum(int code, String message) {
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
