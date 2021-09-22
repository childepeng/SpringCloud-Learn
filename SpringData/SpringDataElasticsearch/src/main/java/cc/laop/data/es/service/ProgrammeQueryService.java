package cc.laop.data.es.service;

import cc.laop.data.es.dataobject.ProgrammeDO;
import cc.laop.data.es.dataobject.ProgrammeQueryDTO;
import cc.laop.data.es.dataobject.QueryResult;
import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.index.query.*;
import org.elasticsearch.search.aggregations.bucket.MultiBucketsAggregation;
import org.elasticsearch.search.aggregations.bucket.histogram.DateHistogramAggregationBuilder;
import org.elasticsearch.search.aggregations.bucket.histogram.DateHistogramInterval;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Auther: peng
 * @Date: create in 2021/8/4 14:53
 * @Description:
 */
@Service
public class ProgrammeQueryService {

    @Autowired
    private ElasticsearchRestTemplate restTemplate;

    private static final Logger logger = LoggerFactory.getLogger(ProgrammeQueryService.class);


    public QueryResult search(ProgrammeQueryDTO params) {
        logger.debug("Search params: {}", JSON.toJSONString(params));
        SearchHits<ProgrammeDO> hits = restTemplate.search(buildQuery(params), ProgrammeDO.class);
        QueryResult result = new QueryResult();
        result.setPageNum(params.getPageNum())
                .setPageSize(params.getPageSize());
        if (hits != null) {
            final List<ProgrammeDO> list = hits
                    .stream().map(it -> it.getContent()).collect(Collectors.toList());
            result.setTotal((int) hits.getTotalHits()).setList(list);
        } else {
            result.setTotal(0).setList(new ArrayList());
        }
        logger.debug("Search Result: {}", JSON.toJSONString(result));
        return result;
    }


    /**
     * 通过 NativeSearchQueryBuilder 构建查询条件
     *
     * @param params
     * @return
     */
    public NativeSearchQuery buildQuery(ProgrammeQueryDTO params) {

        NativeSearchQueryBuilder builder = new NativeSearchQueryBuilder();
        if (ArrayUtils.isNotEmpty(params.getProgrammeIds())) {
            List<String> ids = new ArrayList<>();
            for (int i = 0; i < params.getProgrammeIds().length; i++) {
                ids.add(String.valueOf(params.getProgrammeIds()[i]));
            }
            builder.withQuery(new IdsQueryBuilder().addIds(ids.toArray(new String[ids.size()])));
            logger.debug("NativeSearchQuery: {}", builder.toString());
            return builder.build();
        }

        if (params.getPageSize() != null) {
            if (StringUtils.isNotEmpty(params.getOrderField())) {
                builder.withPageable(PageRequest.of(params.getPageNum(), params.getPageSize(),
                        Sort.by(params.getOrderField())));
            } else {
                builder.withPageable(PageRequest.of(params.getPageNum(), params.getPageSize()));
            }
        }

        BoolQueryBuilder bool = new BoolQueryBuilder();
        if (StringUtils.isNotEmpty(params.getKeyword())) {
            BoolQueryBuilder bool2 = new BoolQueryBuilder();
            // 节目全称匹配
            bool2.should(new TermQueryBuilder("fullName", params.getKeyword()));
            // 名称首字母缩写匹配，使用前缀查询
            bool2.should(new PrefixQueryBuilder("acronym", params.getKeyword().toUpperCase()));
            bool2.should(new TermQueryBuilder("imdbid", params.getKeyword()));
            bool2.should(new TermQueryBuilder("tags", params.getKeyword()));
            bool2.should(new TermQueryBuilder("columns", params.getKeyword()));
            bool2.should(new TermQueryBuilder("casts", params.getKeyword()));

            MatchQueryBuilder mqb = new MatchQueryBuilder("name", params.getKeyword());
            // 权重
            mqb.boost(2);
            // 名称匹配度需要达到75%
            mqb.minimumShouldMatch("75%");
            bool2.should(mqb);

            // 以上必须有一个能匹配上
            bool2.minimumShouldMatch(1);
            bool.must(bool2);
        }

        if (params.getServiceTypeId() != null) {
            bool.must(new TermQueryBuilder("serviceTypeIds", params.getServiceTypeId()));
        }

        if (BooleanUtils.isTrue(params.getAdult())) {
            bool.must(new TermQueryBuilder("progType", 2));
        } else if (BooleanUtils.isTrue(params.getLock())) {
            bool.must(new TermQueryBuilder("progType", 1));
        }

        if (ArrayUtils.isNotEmpty(params.getTagIds())) {
            for (Integer tagId : params.getTagIds()) {
                bool.must(new TermQueryBuilder("tagIds", tagId));
            }
        }

        if (ArrayUtils.isNotEmpty(params.getTypes())) {
            for (Integer type : params.getTypes()) {
                bool.should(new TermQueryBuilder("type", type));
            }
            bool.minimumShouldMatch(1);
        }

        if (ArrayUtils.isNotEmpty(params.getColumnIds())) {
            for (Integer columnId : params.getColumnIds()) {
                bool.must(new TermQueryBuilder("columnIds", columnId));
            }
        }
        bool.must(new TermQueryBuilder("status", 1));
        builder.withQuery(bool);
        logger.debug("NativeSearchQuery: {}", bool.toString());
        return builder.build();

    }


    /**
     * 聚合统计
     * 统计每月新增节目数量
     *
     * @return
     */
    public List<MultiBucketsAggregation.Bucket> createtimeHistogram() {
        NativeSearchQueryBuilder builder = new NativeSearchQueryBuilder();
        builder.withPageable(PageRequest.of(0, 1))
                .addAggregation(new DateHistogramAggregationBuilder("createtime_histogram")
                        .field("createTime")
                        .calendarInterval(DateHistogramInterval.MONTH)
                );
        SearchHits<ProgrammeDO> search = restTemplate.search(builder.build(), ProgrammeDO.class);

        MultiBucketsAggregation mba = (MultiBucketsAggregation) search.getAggregations().asList().get(0);
        return (List<MultiBucketsAggregation.Bucket>) mba.getBuckets();
    }

}
