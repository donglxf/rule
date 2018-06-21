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
public class OrderByActionImpl extends DroolsActionService {
    /**
     * Date 2017/7/24
     * Author lihao [lihao@sinosoft.com]
     * <p>
     * 方法说明: 默认执行方法
     *
     * @param fact   参数
     * @param result 结果集
     * @param key    // 业务变量
     * @param grade  // 权值
     */
    public void grade(RuleExecutionObject fact, RuleExecutionResult result, String key, String grade) {
        //都执行的东西
      //  execute(fact,result,key);

        double scope = 0;
        Object total = result.getMap().get("scope");
        if (null != total) {
            scope = Double.parseDouble(String.valueOf(total));
        }
        Object val = result.getMap().get(key);
        Object gra = result.getMap().get(grade);
        double fen =  CalculateUtils.mul4Obj(val,gra);
        scope += fen;
        result.getMap().put("scope", scope);
       //设置分值
        log.info("TestActionImpl 执行");
        System.err.println("总得分>>>>>>>>::" + result.getMap().get("scope"));
        //统一记录
        List<DroolsActionForm> list = result.getDefalutActions();
        DroolsActionForm actionForm = new DroolsActionForm();
        log.debug("--------设置执行结果--------");
        String rule = (String) result.getMap().get("rule");
        actionForm.setResult(Arrays.asList(fen+""));
        actionForm.setRuleName(rule);
        if(list == null ){
            list = new ArrayList<>();
        }
        list.add(actionForm);
        result.setDefalutActions(list);
    }

}
