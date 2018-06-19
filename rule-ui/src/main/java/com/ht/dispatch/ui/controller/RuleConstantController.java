package com.ht.dispatch.ui.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


/**
* @ClassName: RuleConstantController
* @Description: 规则库常量
* @author dyb
* @date 2018年1月3日 上午9:42:12
* 
*/
@Controller
@RequestMapping("/dispatch")
public class RuleConstantController {

    @RequestMapping(value = "/constant",method = RequestMethod.GET)
    public String ruleConstant(){
    	return "rule/constant/rule_constant";
    }
    
    
    @RequestMapping(value = "/constant/edit",method = RequestMethod.GET)
    public String ruleConstantEdit(){
    	return "rule/constant/rule_constant_edit";
    }
    
    @RequestMapping(value = "/constant/itemEdit",method = RequestMethod.GET)
    public String ruleConstantItemEdit(){
    	return "rule/constant/rule_constant_item_edit";
    }

}
