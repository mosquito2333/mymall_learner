package com.mymall.pojo;

import java.util.Date;

public class Category {
    private Integer cateid;

    private Integer parentid;

    private String catename;

    private Boolean catestatus;

    private Integer sortorder;

    private Date createTime;

    private Date updateTime;

    public Category(Integer cateid, Integer parentid, String catename, Boolean catestatus, Integer sortorder, Date createTime, Date updateTime) {
        this.cateid = cateid;
        this.parentid = parentid;
        this.catename = catename;
        this.catestatus = catestatus;
        this.sortorder = sortorder;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    public Category() {
        super();
    }

    public Integer getCateid() {
        return cateid;
    }

    public void setCateid(Integer cateid) {
        this.cateid = cateid;
    }

    public Integer getParentid() {
        return parentid;
    }

    public void setParentid(Integer parentid) {
        this.parentid = parentid;
    }

    public String getCatename() {
        return catename;
    }

    public void setCatename(String catename) {
        this.catename = catename == null ? null : catename.trim();
    }

    public Boolean getCatestatus() {
        return catestatus;
    }

    public void setCatestatus(Boolean catestatus) {
        this.catestatus = catestatus;
    }

    public Integer getSortorder() {
        return sortorder;
    }

    public void setSortorder(Integer sortorder) {
        this.sortorder = sortorder;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}