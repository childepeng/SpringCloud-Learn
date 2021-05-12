package cc.laop.gateway.util;

/**
 * @Auther: peng
 * @Date: create in 2021/5/12 10:36
 * @Description:
 */
public class StringUtils {

    /**
     * 任何一个为空
     *
     * @param args
     * @return true:存在空值；false:不存在空值
     */
    public static boolean isAnyEmpty(String... args) {
        if (args == null || args.length == 0) {
            return true;
        }
        for (String arg : args) {
            if (arg == null || arg.length() == 0) {
                return true;
            }
        }
        return false;
    }

}
