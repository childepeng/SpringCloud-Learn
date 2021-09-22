package cc.laop.data.es.dataobject;

import java.util.List;

/**
 * @Auther: peng
 * @Date: create in 2021/8/4 15:01
 * @Description:
 */
public class QueryResult {

    private int total;
    private int pageNum;
    private int pageSize;
    private List list;

    public int getTotal() {
        return total;
    }

    public QueryResult setTotal(int total) {
        this.total = total;
        return this;
    }

    public int getPageNum() {
        return pageNum;
    }

    public QueryResult setPageNum(int pageNum) {
        this.pageNum = pageNum;
        return this;
    }

    public int getPageSize() {
        return pageSize;
    }

    public QueryResult setPageSize(int pageSize) {
        this.pageSize = pageSize;
        return this;
    }

    public List getList() {
        return list;
    }

    public QueryResult setList(List list) {
        this.list = list;
        return this;
    }
}
