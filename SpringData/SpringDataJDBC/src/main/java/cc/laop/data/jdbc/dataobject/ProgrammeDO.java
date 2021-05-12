package cc.laop.data.jdbc.dataobject;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.util.Date;

/**
 * @Auther: peng
 * @Date: create in 2021/5/10 18:13
 * @Description:
 */
@Table("b_programme")
public class ProgrammeDO {

    @Id
    private int id;

    private String name;

    @Column("protype")
    private Integer progType;
    private Integer type;
    private String imdbid;

    @Column("property6")
    private String imdbScore;

    @Column("createtime")
    private Date createTime;

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

    public Integer getProgType() {
        return progType;
    }

    public ProgrammeDO setProgType(Integer progType) {
        this.progType = progType;
        return this;
    }

    public Integer getType() {
        return type;
    }

    public ProgrammeDO setType(Integer type) {
        this.type = type;
        return this;
    }

    @Override
    public String toString() {
        return "ProgrammeDO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", progType=" + progType +
                ", type=" + type +
                ", imdbid='" + imdbid + '\'' +
                ", imdbScore='" + imdbScore + '\'' +
                ", createTime=" + createTime +
                '}';
    }
}
