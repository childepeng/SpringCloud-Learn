package cc.laop.gateway.config;

import com.alibaba.csp.sentinel.adapter.gateway.sc.SentinelGatewayFilter;
import com.alibaba.csp.sentinel.adapter.gateway.sc.exception.SentinelGatewayBlockExceptionHandler;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.web.reactive.result.view.ViewResolver;

import javax.annotation.PostConstruct;
import java.util.Collections;
import java.util.List;

/**
 * @Auther: peng
 * @Date: create in 2021/5/17 16:54
 * @Description: sentinel配置
 */
@Configuration
public class SentinelConfig {

    private final List<ViewResolver> viewResolvers;
    private final ServerCodecConfigurer serverCodecConfigurer;

    public SentinelConfig(ObjectProvider<List<ViewResolver>> viewResolversProvider,
                          ServerCodecConfigurer serverCodecConfigurer) {
        this.viewResolvers = viewResolversProvider.getIfAvailable(Collections::emptyList);
        this.serverCodecConfigurer = serverCodecConfigurer;
    }


    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public SentinelGatewayBlockExceptionHandler sentinelGatewayBlockExceptionHandler() {
        return new SentinelGatewayBlockExceptionHandler(viewResolvers, serverCodecConfigurer);
    }

    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public GlobalFilter sentinelGatewayFilter() {
        return new SentinelGatewayFilter();
    }

    @PostConstruct
    public void init() {
        initApis();
        initGatewayRules();
    }

    /**
     * ApiDefinition: 用户自定义的 API 定义分组，可以看做是一些 URL 匹配的组合，限流的时候可以针对这个自定义的 API 分组维度进行限流。
     * sentinel控制台支持动态添加规则
     */
    public void initApis() {
        // Set<ApiDefinition> definitions = new HashSet<>();
        // ApiDefinition api2 = new ApiDefinition("api-prog")
        //         .setPredicateItems(new HashSet<ApiPredicateItem>() {{
        //             add(new ApiPathPredicateItem().setPattern("/p2/**"));
        //             add(new ApiPathPredicateItem().setPattern("/p1/**")
        //                     .setMatchStrategy(SentinelGatewayConstants.URL_MATCH_STRATEGY_PREFIX));
        //         }});
        // definitions.add(api2);
        // GatewayApiDefinitionManager.loadApiDefinitions(definitions);
    }

    /**
     * GatewayFlowRule: 网关限流规则，针对 API Gateway 的场景定制的限流规则，可以针对不同 route 或自定义的 API 分组进行限流，支持针对请求中的参数、Header、来源 IP 等进行定制化的限流
     * sentinel控制台可动态添加规则
     */
    public void initGatewayRules() {
        // Set<GatewayFlowRule> rules = new HashSet<>();
        // rules.add(new GatewayFlowRule("es-test2").setCount(2).setIntervalSec(10));
        // rules.add(new GatewayFlowRule("api-prog").setCount(5).setIntervalSec(20));
        // GatewayRuleManager.loadRules(rules);
    }

}
