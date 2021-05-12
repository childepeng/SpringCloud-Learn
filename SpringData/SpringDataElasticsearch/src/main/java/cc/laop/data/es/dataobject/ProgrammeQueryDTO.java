package cc.laop.data.es.dataobject;

/**
 * @Auther: peng
 * @Date: create in 2021/5/10 16:35
 * @Description:
 */
public class ProgrammeQueryDTO {

    private Integer id;

    private String name;

    private String imdbid;

    private Integer type;

    private Integer progType;

    private String[] imdbScoeRange;

    private Integer serviceTypeid;

    private Integer[] tagIds;

    private Integer[] columnIds;

    private Integer pageNum;
    private Integer pageSize;
    private String order;

    public Integer getId() {
        return id;
    }

    public ProgrammeQueryDTO setId(Integer id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public ProgrammeQueryDTO setName(String name) {
        this.name = name;
        return this;
    }

    public String getImdbid() {
        return imdbid;
    }

    public ProgrammeQueryDTO setImdbid(String imdbid) {
        this.imdbid = imdbid;
        return this;
    }

    public Integer getType() {
        return type;
    }

    public ProgrammeQueryDTO setType(Integer type) {
        this.type = type;
        return this;
    }

    public Integer getProgType() {
        return progType;
    }

    public ProgrammeQueryDTO setProgType(Integer progType) {
        this.progType = progType;
        return this;
    }

    public String[] getImdbScoeRange() {
        return imdbScoeRange;
    }

    public ProgrammeQueryDTO setImdbScoeRange(String[] imdbScoeRange) {
        this.imdbScoeRange = imdbScoeRange;
        return this;
    }

    public Integer getServiceTypeid() {
        return serviceTypeid;
    }

    public ProgrammeQueryDTO setServiceTypeid(Integer serviceTypeid) {
        this.serviceTypeid = serviceTypeid;
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

    public Integer getPageNum() {
        return pageNum;
    }

    public ProgrammeQueryDTO setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
        return this;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public ProgrammeQueryDTO setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
        return this;
    }

    public String getOrder() {
        return order;
    }

    public ProgrammeQueryDTO setOrder(String order) {
        this.order = order;
        return this;
    }
}
