package cc.laop.data.es.service;

import cc.laop.data.es.dataobject.ProgrammeDO;
import cc.laop.data.es.dataobject.ProgrammeQueryDTO;
import cc.laop.data.es.repo.ProgrammeRepository;
import org.elasticsearch.search.aggregations.bucket.MultiBucketsAggregation;
import org.elasticsearch.search.aggregations.bucket.histogram.DateHistogramAggregationBuilder;
import org.elasticsearch.search.aggregations.bucket.histogram.DateHistogramInterval;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.Criteria;
import org.springframework.data.elasticsearch.core.query.CriteriaQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Auther: peng
 * @Date: create in 2021/5/10 16:05
 * @Description:
 */
@Service
public class ProgrammeService {
    @Autowired
    private ProgrammeRepository programmeRepository;
    @Autowired
    private ElasticsearchRestTemplate restTemplate;

    public void save(ProgrammeDO programmeDO) {
        programmeRepository.save(programmeDO);
    }

    /**
     * @param name
     * @param pageNum  起始页为 0
     * @param pageSize
     * @return
     */
    public List<ProgrammeDO> queryByName(String name, int pageNum, int pageSize) {
        return programmeRepository.findByName(name, PageRequest.of(pageNum, pageSize));
    }


    /**
     * 复合查询
     *
     * @param queryDO
     * @return
     */
    public List<ProgrammeDO> compoundQuery(ProgrammeQueryDTO queryDO) {

        if (queryDO == null) {
            return new ArrayList<>();
        }
        Criteria criteria = new Criteria();
        if (queryDO.getId() != null) {
            criteria.and(new Criteria("id").is(queryDO.getId()));
        }
        // if (StringUtils.hasLength(queryDO.getName())) {
        //     criteria.and(new Criteria("name").matches(queryDO.getName()));
        // }
        if (StringUtils.hasLength(queryDO.getImdbid())) {
            criteria.and(new Criteria("imdbid").is(queryDO.getImdbid()));
        }
        if (queryDO.getImdbScoeRange() != null && queryDO.getImdbScoeRange().length == 2) {
            criteria.and(new Criteria("imdbScore").between(queryDO.getImdbScoeRange()[0],
                    queryDO.getImdbScoeRange()[1]));
        }
        if (queryDO.getType() != null) {
            criteria.and(new Criteria("type").is(queryDO.getType()));
        }
        if (queryDO.getProgType() != null) {
            criteria.and(new Criteria("progType").is(queryDO.getProgType()));
        }
        if (queryDO.getTagIds() != null && queryDO.getTagIds().length > 0) {
            for (Integer tagId : queryDO.getTagIds()) {
                criteria.and(new Criteria("tagIds").is(tagId));
            }
        }
        if (queryDO.getColumnIds() != null && queryDO.getColumnIds().length > 0) {
            for (Integer columnId : queryDO.getColumnIds()) {
                criteria.and(new Criteria("columnIds").is(columnId));
            }
        }
        if (queryDO.getServiceTypeid() != null) {
            criteria.and(new Criteria("serviceTypeIds").is(queryDO.getServiceTypeid()));
        }
        Query query = new CriteriaQuery(criteria);
        if (queryDO.getPageNum() != null) {
            query.setPageable(PageRequest.of(queryDO.getPageNum(), queryDO.getPageSize()));
        }

        SearchHits<ProgrammeDO> hits = restTemplate.search(query, ProgrammeDO.class);
        if (hits != null) {
            return hits.stream().map(SearchHit::getContent).collect(Collectors.toList());
        }
        return new ArrayList<>();
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
