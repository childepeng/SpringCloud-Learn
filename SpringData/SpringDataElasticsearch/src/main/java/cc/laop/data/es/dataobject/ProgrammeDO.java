package cc.laop.data.es.dataobject;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.Date;

/**
 * @Auther: Administrator
 * @Date: create in 2021/5/10 15:59
 * @Description:
 */
@Document(indexName = "programme", shards = 3)
public class ProgrammeDO {

    @Id
    private int id;

    @Field(type = FieldType.Text)
    private String name;

    @Field(type = FieldType.Keyword)
    private String fullName;

    @Field(type = FieldType.Integer)
    private Integer type;

    @Field(type = FieldType.Integer)
    private Integer progType;

    @Field(type = FieldType.Keyword)
    private String imdbid;

    @Field(type = FieldType.Text)
    private String imdbScore;

    @Field(type = FieldType.Date, format = DateFormat.custom, pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    @Field(type = FieldType.Text)
    private String releaseTime;

    @Field(type = FieldType.Integer)
    private Integer[] castIds;

    @Field(type = FieldType.Integer)
    private Integer[] tagIds;

    @Field(type = FieldType.Integer)
    private Integer[] columnIds;

    @Field(type = FieldType.Integer)
    private Integer[] serviceTypeIds;

    public int getId() {
        return id;
    }

    public ProgrammeDO setId(int id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public ProgrammeDO setName(String name) {
        this.name = name;
        return this;
    }

    public String getFullName() {
        return fullName;
    }

    public ProgrammeDO setFullName(String fullName) {
        this.fullName = fullName;
        return this;
    }

    public Integer getType() {
        return type;
    }

    public ProgrammeDO setType(Integer type) {
        this.type = type;
        return this;
    }

    public Integer getProgType() {
        return progType;
    }

    public ProgrammeDO setProgType(Integer progType) {
        this.progType = progType;
        return this;
    }

    public String getImdbid() {
        return imdbid;
    }

    public ProgrammeDO setImdbid(String imdbid) {
        this.imdbid = imdbid;
        return this;
    }

    public String getImdbScore() {
        return imdbScore;
    }

    public ProgrammeDO setImdbScore(String imdbScore) {
        this.imdbScore = imdbScore;
        return this;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public ProgrammeDO setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }

    public String getReleaseTime() {
        return releaseTime;
    }

    public ProgrammeDO setReleaseTime(String releaseTime) {
        this.releaseTime = releaseTime;
        return this;
    }

    public Integer[] getCastIds() {
        return castIds;
    }

    public ProgrammeDO setCastIds(Integer[] castIds) {
        this.castIds = castIds;
        return this;
    }

    public Integer[] getTagIds() {
        return tagIds;
    }

    public ProgrammeDO setTagIds(Integer[] tagIds) {
        this.tagIds = tagIds;
        return this;
    }

    public Integer[] getColumnIds() {
        return columnIds;
    }

    public ProgrammeDO setColumnIds(Integer[] columnIds) {
        this.columnIds = columnIds;
        return this;
    }

    public Integer[] getServiceTypeIds() {
        return serviceTypeIds;
    }

    public ProgrammeDO setServiceTypeIds(Integer[] serviceTypeIds) {
        this.serviceTypeIds = serviceTypeIds;
        return this;
    }
}
