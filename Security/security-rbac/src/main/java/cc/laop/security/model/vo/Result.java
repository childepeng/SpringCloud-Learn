package cc.laop.security.model.vo;

import cc.laop.security.constant.ResultCodeEnum;

/**
 * @Auther: peng
 * @Date: create in 2021/5/21 10:44
 * @Description:
 */
public class Result {

    private int code;
    private String message;
    private Object data;

    public static Result of(ResultCodeEnum resultCodeEnum) {
        return new Result().setCode(resultCodeEnum.getCode())
                .setMessage(resultCodeEnum.getMessage());
    }

    public int getCode() {
        return code;
    }

    public Result setCode(int code) {
        this.code = code;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public Result setMessage(String message) {
        this.message = message;
        return this;
    }

    public Object getData() {
        return data;
    }

    public Result setData(Object data) {
        this.data = data;
        return this;
    }
}
