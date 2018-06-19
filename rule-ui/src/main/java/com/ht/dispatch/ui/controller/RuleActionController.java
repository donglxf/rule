package com.ht.dispatch.ui.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


/**
* @ClassName: RuleActionController
* @Description: 规则库动作
* @author dyb
* @date 2018年1月3日 上午9:42:59
* 
*/
@Controller
@RequestMapping("/ruleAction")
public class RuleActionController {

    
    @RequestMapping(value = "/index",method = RequestMethod.GET)
    public String ruleAction(){
    	return "rule/action/rule_action";
    }
    
    @RequestMapping(value = "/edit",method = RequestMethod.GET)
    public String ruleActionEdit(){
    	return "rule/action/rule_action_edit";
    }
    @RequestMapping(value = "/actionParamEdit",method = RequestMethod.GET)
    public String ruleActionParamEdit(){
    	return "rule/action/rule_action_param_edit";
    }
    
}
