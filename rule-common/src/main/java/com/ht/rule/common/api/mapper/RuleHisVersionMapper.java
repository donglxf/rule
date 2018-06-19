package com.ht.rule.common.api.mapper;

import com.ht.rule.common.mapper.SuperMapper;
import com.ht.rule.common.api.entity.RuleHisVersion;
import com.ht.rule.common.api.vo.RuleHisVersionVo;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zhangzhen
 * @since 2018-01-10
 */
public interface RuleHisVersionMapper extends SuperMapper<RuleHisVersion> {

    List<RuleHisVersionVo> getRuleValidationResult(Map<String, Object> map);

    List<Map<String,Object>> getRuleBatchValidationResult(Map<String, Object> map);

    List<RuleHisVersion> getHisVersionListByVidName(Map<String, Object> paramMap);

}
