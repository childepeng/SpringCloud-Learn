package cc.laop.data.es.dataobject;

/**
 * @Auther: peng
 * @Date: create in 2021/5/10 16:35
 * @Description:
 */
public class ProgrammeQueryDTO {

    private String keyword;

    private Integer serviceTypeId;

    private Boolean adult;

    private Boolean lock;

    private Integer[] programmeIds;

    private Integer[] tagIds;

    private Integer[] columnIds;

    private String orderField;

    private Integer[] types;

    private Integer pageSize;
    private Integer pageNum;

    public String getKeyword() {
        return keyword;
    }

    public ProgrammeQueryDTO setKeyword(String keyword) {
        this.keyword = keyword;
        return this;
    }

    public Integer getServiceTypeId() {
        return serviceTypeId;
    }

    public ProgrammeQueryDTO setServiceTypeId(Integer serviceTypeId) {
        this.serviceTypeId = serviceTypeId;
        return this;
    }

    public Boolean getAdult() {
        return adult;
    }

    public ProgrammeQueryDTO setAdult(Boolean adult) {
        this.adult = adult;
        return this;
    }

    public Boolean getLock() {
        return lock;
    }

    public ProgrammeQueryDTO setLock(Boolean lock) {
        this.lock = lock;
        return this;
    }

    public Integer[] getProgrammeIds() {
        return programmeIds;
    }

    public ProgrammeQueryDTO setProgrammeIds(Integer[] programmeIds) {
        this.programmeIds = programmeIds;
        return this;
    }

    public Integer[] getTagIds() {
        return tagIds;
    }

    public ProgrammeQueryDTO setTagIds(Integer[] tagIds) {
        this.tagIds = tagIds;
        return this;
    }

    public Integer[] getColumnIds() {
        return columnIds;
    }

    public ProgrammeQueryDTO setColumnIds(Integer[] columnIds) {
        this.columnIds = columnIds;
        return this;
    }

    public String getOrderField() {
        return orderField;
    }

    public ProgrammeQueryDTO setOrderField(String orderField) {
        this.orderField = orderField;
        return this;
    }

    public Integer[] getTypes() {
        return types;
    }

    public ProgrammeQueryDTO setTypes(Integer[] types) {
        this.types = types;
        return this;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public ProgrammeQueryDTO setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
        return this;
    }

    public Integer getPageNum() {
        return pageNum;
    }

    public ProgrammeQueryDTO setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
        return this;
    }
}
