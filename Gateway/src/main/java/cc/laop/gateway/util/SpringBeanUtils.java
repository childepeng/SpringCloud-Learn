package cc.laop.gateway.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * @Auther: peng
 * @Date: create in 2021/5/11 10:08
 * @Description:
 */
@Component
public class SpringBeanUtils implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringBeanUtils.applicationContext = applicationContext;
    }

    public static <T> T getBean(String name) {
        if (applicationContext != null) {
            return (T) applicationContext.getBean(name);
        } else {
            return null;
        }
    }

}
