package cc.laop.gateway.filter.rewrite;

/**
 * @Auther: peng
 * @Date: create in 2021/5/11 9:58
 * @Description:
 */
public class Config {

    private String handler;

    private String contentType;

    public String getContentType() {
        return contentType;
    }

    public Config setContentType(String contentType) {
        this.contentType = contentType;
        return this;
    }

    public String getHandler() {
        return handler;
    }

    public Config setHandler(String handler) {
        this.handler = handler;
        return this;
    }
}
