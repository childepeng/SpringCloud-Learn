package cc.laop.gateway.filter.rewrite;

import cc.laop.gateway.util.SpringBeanUtils;

/**
 * @Auther: peng
 * @Date: create in 2021/5/11 10:02
 * @Description:
 */
public interface RewriteHandler {

    /**
     * 编码、加密
     *
     * @param content
     * @return
     */
    String encode(String content);

    /**
     * 解码、解密
     *
     * @param content
     * @return
     */
    String decode(String content);


    static RewriteHandler getHandler(String handlerName) {
        RewriteHandler handler = SpringBeanUtils.getBean(handlerName);
        if (handler == null) {
            return new RewriteHandler() {
                @Override
                public String encode(String content) {
                    return content;
                }

                @Override
                public String decode(String content) {
                    return content;
                }
            };
        } else {
            return handler;
        }
    }

}
