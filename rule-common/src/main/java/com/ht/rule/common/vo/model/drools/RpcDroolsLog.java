package com.ht.rule.common.vo.model.drools;

import java.io.Serializable;
import java.util.Date;

public class RpcDroolsLog implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    /**
     * 模型实例id
     */
    private String procinstId;
    /**
     * 模型名
     */
    private String modelName;
    /**
     * 決策版本流水
     */
    private String senceVersionid;
    /**
     * 入参
     */
    private String inParamter;
    /**
     * 计算结果
     */
    private String outParamter;
    /**
     * 命中规则总数
     */
    private Integer executeTotal;
    /**
     * 决策执行类型：0-直接调用，1-模型调用
     */
    private String type;
    /**
     * 插入时间
     */
    private Date createTime;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getProcinstId() {
        return procinstId;
    }

    public void setProcinstId(String procinstId) {
        this.procinstId = procinstId;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public String getSenceVersionid() {
        return senceVersionid;
    }

    public void setSenceVersionid(String senceVersionid) {
        this.senceVersionid = senceVersionid;
    }

    public String getInParamter() {
        return inParamter;
    }

    public void setInParamter(String inParamter) {
        this.inParamter = inParamter;
    }

    public String getOutParamter() {
        return outParamter;
    }

    public void setOutParamter(String outParamter) {
        this.outParamter = outParamter;
    }

    public Integer getExecuteTotal() {
        return executeTotal;
    }

    public void setExecuteTotal(Integer executeTotal) {
        this.executeTotal = executeTotal;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

}
