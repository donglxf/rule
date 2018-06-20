package com.ht.rule.config.rpc;

import com.ht.rule.common.vo.model.drools.DroolsParamter;
import com.ht.rule.common.vo.model.drools.RuleExcuteResult;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient("rule-drools")
public interface DroolsExcuteFeginClient {
  /**
   * 调用规则引擎
   * @param paramter
   * @return
   */
  @RequestMapping(value = "/excuteDroolsScene")
  public RuleExcuteResult excuteDroolsScene(@RequestBody DroolsParamter paramter);

  /**
   * 评分卡
   * @param paramter
   * @return
   */
  @RequestMapping(value = "/excuteDroolsGrade")
  RuleExcuteResult excuteDroolsGrade(@RequestBody DroolsParamter paramter);
}