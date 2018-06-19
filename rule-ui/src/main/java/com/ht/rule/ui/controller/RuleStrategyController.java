package com.ht.rule.ui.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 实体对象-变量页面跳转相关
 *
 * @author dyb
 * @create 2017-06-06 13:34
 */
@Controller
@RequestMapping("/strategy/")
public class RuleStrategyController {
    @RequestMapping(value = "index",method = RequestMethod.GET)
    public String strategyIndex(){
        return "rule/strategy/strategyIndex";
    }

    @RequestMapping(value = "manualTest",method = RequestMethod.GET)
    public String ruleEntityItemEdit(){
        return "rule/strategy/manualTest";
    }

    @RequestMapping(value = "autoTest",method = RequestMethod.GET)
    public String ruleEntity(){
        return "rule/strategy/autoTest";
    }

}
