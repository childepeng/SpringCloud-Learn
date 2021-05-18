package cc.laop.dubbo.rest;

import cc.laop.dubbo.api.EsProgrammeService;
import org.apache.dubbo.config.annotation.DubboService;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

/**
 * @Auther: peng
 * @Date: create in 2021/5/13 10:40
 * @Description:
 */
@DubboService(protocol = "rest")
@Path("programme")
@Produces({"application/json"})
@Consumes({"application/json"})
public class EsProgrammeServiceImpl implements EsProgrammeService {

    /**
     * Consumes 指定接收的参数类型，比如json, dubbo会将json自动转换为java对象
     * Produces 指定输出数据类型，比如json, dubbo会将java对象转换为json字符串
     *
     * @param params
     * @return
     */
    @POST
    @Path("search")
    @Override
    public String search(String params) {
        return "hello: " + params;
    }

}
