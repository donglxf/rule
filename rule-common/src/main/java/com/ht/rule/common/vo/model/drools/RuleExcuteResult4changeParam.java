package com.ht.rule.common.vo.model.drools;

import com.ht.rule.common.result.Result;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.poi.ss.formula.functions.T;

import java.util.List;
import java.util.Map;

@Data
@ApiModel
public class RuleExcuteResult4changeParam extends RuleExcuteResult{
    public RuleExcuteResult4changeParam(){
        super();
    }
    public RuleExcuteResult4changeParam(int code, String msg, List<Map<String,Object>> datas, String senceVersoionId) {
        super(code,msg,senceVersoionId);
        this.datas = datas;
    }
    public RuleExcuteResult4changeParam(int code, String msg, List<Map<String,Object>> datas, String senceVersoionId,List<DroolsActionForm> data) {
        super(code,msg,data,senceVersoionId);
        this.datas = datas;
    }
    @ApiModelProperty(value = "转换后的数据集合")
    private List<Map<String,Object>> datas;

    public static RuleExcuteResult4changeParam error(int code, String msg) {
        RuleExcuteResult4changeParam result = new RuleExcuteResult4changeParam();
        result.setCode(code);
        result.setMsg(msg);
        return result;
    }

}
