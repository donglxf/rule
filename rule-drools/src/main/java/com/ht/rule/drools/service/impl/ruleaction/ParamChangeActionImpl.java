package com.ht.rule.drools.service.impl.ruleaction;

import com.ht.rule.common.util.CalculateUtils;
import com.ht.rule.common.vo.model.drools.DroolsActionForm;
import com.ht.rule.common.vo.model.drools.RuleExecutionObject;
import com.ht.rule.common.vo.model.drools.RuleExecutionResult;
import com.ht.rule.drools.service.DroolsActionService;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 描述：
 * CLASSPATH: com.sky.TestActionImpl
 * VERSION:   1.0
 * Created by lihao
 * DATE:      2017/7/24
 */
@SuppressWarnings("unchecked")
@Service
@Log4j2
public class ParamChangeActionImpl extends DroolsActionService {
    /**
     * Date 2017/7/24
     * Author lihao [lihao@sinosoft.com]
     * <p>
     * 方法说明: 默认执行方法
     *
     * @param fact   参数
     * @param result 结果集
     */
    public void paramChange(RuleExecutionObject fact, RuleExecutionResult result, String value,String name) {

        super.execute(fact,result,value);

        //参数名
        Object valName = result.getMap().get(name);
        //参数值
        Object valValue = result.getMap().get(value);

        //修改了
        result.getMap().put(valName.toString(),valValue);
    }

}
