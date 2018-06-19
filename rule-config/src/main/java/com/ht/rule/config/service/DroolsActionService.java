package com.ht.rule.config.service;

import com.ht.rule.common.vo.model.drools.DroolsActionForm;
import com.ht.rule.common.vo.model.drools.RuleExecutionObject;
import com.ht.rule.common.vo.model.drools.RuleExecutionResult;
import lombok.extern.log4j.Log4j2;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * 描述： drools 实现类动作接口 CLASSPATH: com.sky.DroolsActionService VERSION: 1.0
 * Created by lihao DATE: 2017/7/24
 */
@SuppressWarnings("unchecked")
@Log4j2
public class DroolsActionService {


	/**
	 * Date 2017/7/24 Author lihao [lihao@sinosoft.com]
	 *
	 * 方法说明: 默认执行方法
	 *
	 * @param fact
	 *            参数
	 * @param result
	 *            结果集
	 */
	public void execute(RuleExecutionObject fact, RuleExecutionResult result, String key){

		List<DroolsActionForm> list = result.getDefalutActions();
		DroolsActionForm actionForm = new DroolsActionForm();
		log.debug("--------设置执行结果--------");
		Object val = result.getMap().get(key);
		String rule = (String) result.getMap().get("rule");
		actionForm.setResult(val.toString());
		actionForm.setRuleName(rule);
		if(list == null ){
			list = new ArrayList<>();
		}
		list.add(actionForm);
	}
	/**
	 * 规则日志
	* @Title: saveLog
	* @Description: 记录执行的规则
	* @param @param fact
	* @param @param result    设定文件
	* @return void    返回类型
	* @throws
	 */
	public void saveLog(RuleExecutionObject fact, RuleExecutionResult result) {
		List<String> ruleList = (List<String>) result.getMap().get("ruleList");
		if (null == ruleList) {
			ruleList = new ArrayList<String>();
		}
		String rule = (String) result.getMap().get("rule");
		ruleList.add(rule);
		result.getMap().put("ruleList", ruleList);

		log.info("########当前命中规则："+rule);
	}

}
