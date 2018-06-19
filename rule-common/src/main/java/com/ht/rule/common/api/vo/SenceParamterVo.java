package com.ht.rule.common.api.vo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SenceParamterVo {

    private String senceName;
    private String senceVersionId;
    private Map<String,Object> data;
    private List<VariableVo> variables;
    private List<HitRuleInfoVo> hitRules;


    public String getSenceName() {
        return senceName;
    }

    public void setSenceName(String senceName) {
        this.senceName = senceName;
    }

    public String getSenceVersionId() {
        return senceVersionId;
    }

    public void setSenceVersionId(String senceVersionId) {
        this.senceVersionId = senceVersionId;
    }

    public List<HitRuleInfoVo> getHitRules() {
        return hitRules;
    }

    public void setHitRules(List<HitRuleInfoVo> hitRules) {
        this.hitRules = hitRules;
    }

    public List<VariableVo> getVariables() {
        return variables;
    }

    public void setVariables(List<VariableVo> variables) {
        this.variables = variables;
    }


    public void addVariableVo(VariableVo vo){
        if(variables == null){
            variables = new ArrayList<VariableVo>();
        }
        variables.add(vo);

    }
    public void addHitRuleVo(HitRuleInfoVo vo){
        if(hitRules == null){
            hitRules = new ArrayList<HitRuleInfoVo>();
        }
        hitRules.add(vo);
    }

    public Map<String, Object> getData() {
        return data;
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }
}
