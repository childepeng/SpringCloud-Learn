package cc.laop.gateway.dto;

/**
 * @Auther: peng
 * @Date: create in 2021/5/12 10:43
 * @Description:
 */
public class BaseResp {

    private int result;

    private String message;

    public BaseResp() {
    }

    public BaseResp(ResultCodEnum rtc) {
        this.result = rtc.getCode();
        this.message = rtc.getMessage();
    }

    public BaseResp(int result) {
        this.result = result;
    }

    public int getResult() {
        return result;
    }

    public BaseResp setResult(int result) {
        this.result = result;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public BaseResp setMessage(String message) {
        this.message = message;
        return this;
    }

}
