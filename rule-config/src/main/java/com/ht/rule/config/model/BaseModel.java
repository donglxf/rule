package com.ht.rule.config.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 描述：
 * CLASSPATH: com.sky.BaseModel
 * VERSION:   1.0
 * Created by lihao
 * DATE:      2017/7/20
 */
public class BaseModel implements Serializable {
    //创建人
    private String creUserId;
    //创建时间
    private Date creTime;
    //是否有效
    private Integer isEffect;
    //备注
    private String remark;

    public String getCreUserId() {
        return creUserId;
    }

    public void setCreUserId(String creUserId) {
        this.creUserId = creUserId;
    }

    public Date getCreTime() {
        return creTime;
    }

    public void setCreTime(Date creTime) {
        this.creTime = creTime;
    }

    public Integer getIsEffect() {
        return isEffect;
    }

    public void setIsEffect(Integer isEffect) {
        this.isEffect = isEffect;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
