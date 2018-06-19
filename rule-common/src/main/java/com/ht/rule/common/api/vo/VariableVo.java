package com.ht.rule.common.api.vo;

import com.ht.rule.common.api.entity.VariableBind;

import java.io.Serializable;
import java.util.Map;

public class VariableVo implements Serializable {

    // 变量中文名称
    private String valibaleEn;
    // 英文名称
    private String valibaleCn;
    // 提交代码
    private String submitName;
    // 描述
    private String desc;
    // 类型
    private String type;
    // 下拉、多选等数据
    private Map<String,String> optionData;

    private String value;

    public VariableVo(){
        super();
    }

    public VariableVo(VariableBind bind){
        this.valibaleCn = bind.getVariableCode();
        this.valibaleCn = bind.getVariableName();
        this.submitName = bind.getVariableCode();
        this.value  = bind.getTmpValue();
        this.type = bind.getDataType();
    }




    public String getSubmitName() {
        return submitName;
    }

    public void setSubmitName(String submitName) {
        this.submitName = submitName;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Map<String, String> getOptionData() {
        return optionData;
    }

    public void setOptionData(Map<String, String> optionData) {
        this.optionData = optionData;
    }

    public String getValibaleEn() {
        return valibaleEn;
    }

    public void setValibaleEn(String valibaleEn) {
        this.valibaleEn = valibaleEn;
    }

    public String getValibaleCn() {
        return valibaleCn;
    }

    public void setValibaleCn(String valibaleCn) {
        this.valibaleCn = valibaleCn;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
