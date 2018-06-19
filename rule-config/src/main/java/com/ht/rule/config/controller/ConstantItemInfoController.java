package com.ht.rule.config.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.ht.rule.common.api.entity.ConstantInfo;
import com.ht.rule.config.service.ConstantInfoService;
import com.ht.rule.common.controller.BaseController;
import com.ht.rule.common.result.PageResult;
import com.ht.rule.common.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

/**
* @ClassName: ConstantItemInfoController
* @Description: 常量细类相关接口
* @author dyb
* @date 2018年1月2日 上午11:29:00
* 
*/
@RestController
@RequestMapping("/constantItemInfo")
@Api(tags = "ConstantItemInfoController", description = "常量对象相关api描述", hidden = true)
public class ConstantItemInfoController extends BaseController {

	@Autowired
	private ConstantInfoService constantInfoService;

	@GetMapping("page")
	@ApiOperation(value = "分页查询")
	public PageResult<List<ConstantInfo>> page(String key, Integer page, Integer limit) {
		Wrapper<ConstantInfo> wrapper = new EntityWrapper<>();
		if (StringUtils.isNotBlank(key)) {
			// wrapper.like("entity_name", key);
			// wrapper.or().like("entity_desc", key);
			// wrapper.or().like("entity_identify", key);
		}
		wrapper.ne("con_type", "1");
		Page<ConstantInfo> pages = new Page<>();
		pages.setCurrent(page);
		pages.setSize(limit);
		pages = constantInfoService.selectPage(pages, wrapper);
		return PageResult.success(pages.getRecords(), pages.getTotal());
	}

	@GetMapping("getAll/")
	@ApiOperation(value = "查询所有的对象")
	public Result<List<ConstantInfo>> getAlls(String conType, String conKey) {
		Wrapper<ConstantInfo> wrapper = new EntityWrapper<>();
		wrapper.ne("con_type", conType);
		wrapper.eq("con_key", conKey);
		List<ConstantInfo> list = constantInfoService.selectList(wrapper);
		// Page<EntityInfo> page = new Page<>();
		// page = entityInfoService.selectPage(page);
		
		return Result.success(list);
	}
	
	@PostMapping("edit")
	@ApiOperation(value = "新增")
	@Transactional()
	public Result<Integer> edit(ConstantInfo entityInfo) {
		entityInfo.setCreTime(new Date());
		entityInfo.setIsEffect(1);
		entityInfo.setConType("1");
		entityInfo.setCreUserId(getUserId());
		constantInfoService.insertOrUpdate(entityInfo);
		return Result.success(0);
	}
}
