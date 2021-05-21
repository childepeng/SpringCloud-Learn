package cc.laop.security.util;

/**
 * @Auther: peng
 * @Date: create in 2021/5/21 16:56
 * @Description:
 */
public class BooleanUtils {

    public static boolean isTure(Boolean bool) {
        if (bool != null && bool) {
            return true;
        }
        return false;
    }

}
