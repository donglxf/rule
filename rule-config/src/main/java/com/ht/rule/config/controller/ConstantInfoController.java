package com.ht.rule.config.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.ht.rule.common.api.entity.ConstantInfo;
import com.ht.rule.common.api.mapper.DelFindMapper;
import com.ht.rule.common.util.StringUtil;
import com.ht.rule.config.service.ConstantInfoService;
import com.ht.rule.config.util.anno.OperationDelete;
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
* @ClassName: ConstantInfoController
* @Description: 常量相关接口
* @author dyb
* @date 2018年1月2日 上午11:27:59
* 
*/
@RestController
@RequestMapping("/constantInfo")
@Api(tags = "ConstantInfoController", description = "常量对象相关api描述", hidden = true)
public class ConstantInfoController extends BaseController {

	@Autowired
	private ConstantInfoService constantInfoService;

	@Autowired
	private DelFindMapper delFindMapper;

	@GetMapping("page")
	@ApiOperation(value = "分页查询")
	public PageResult<List<ConstantInfo>> page(String key, String businessId, Integer page, Integer limit) {
		Wrapper<ConstantInfo> wrapper = new EntityWrapper<>();
		if (StringUtils.isNotBlank(key)) {
			 wrapper.like("con_key", key);
			 wrapper.or().like("con_name", key);
			 wrapper.or().like("con_code", key);
		}
		if(StringUtils.isNotBlank( businessId )){
			wrapper.andNew().eq("business_id",businessId);
		}
		wrapper.andNew("con_type<>1");
		Page<ConstantInfo> pages = new Page<>();
		pages.setCurrent(page);
		pages.setSize(limit);
		pages = constantInfoService.selectPage(pages, wrapper);
		return PageResult.success(pages.getRecords(), pages.getTotal());
	}

	@GetMapping("getAll")
	@ApiOperation(value = "查询所有的对象")
	public Result<List<ConstantInfo>> getAll(String key) {
		List<ConstantInfo> list = constantInfoService.selectList(null);
		// Page<EntityInfo> page = new Page<>();
		// page = entityInfoService.selectPage(page);

		return Result.success(list);
	}

	@GetMapping("getOneType")
	@ApiOperation(value = "得到一级常量")
	public Result<List<ConstantInfo>> getOneType() {
		Wrapper<ConstantInfo> wrapper = new EntityWrapper<>();
		wrapper.eq("con_type",0);
		List<ConstantInfo> list = constantInfoService.selectList(wrapper);

		return Result.success(list);
	}

	@GetMapping("delete")
	@ApiOperation(value = "通过id删除父常量信息")
	@OperationDelete(tableColumn = {"rule_entity_item_info&constant_id"},idVal = "#id")
	public Result<Integer> delete( Long id) {
		ConstantInfo constantInfo = constantInfoService.selectById(id);
		if("0".equals(constantInfo.getConType())){  // 父节点
			Wrapper<ConstantInfo> wrapper = new EntityWrapper<>();
			wrapper.eq("con_key",constantInfo.getConKey());
			wrapper.eq("con_type","1");
			List<ConstantInfo> list=constantInfoService.selectList(wrapper);
			list.forEach(t -> {
				constantInfoService.deleteById(t.getConId());
			});
		}
		constantInfoService.deleteById(id);
		return Result.success(0);
	}

	@GetMapping("deleteItemConstant")
	@ApiOperation(value = "通过id删除子常量信息")
	public Result<Integer> deleteItemConstantById( Long id) {
		ConstantInfo constantInfo = constantInfoService.selectById(id);
		if("1".equals(constantInfo.getConType())){  // 子节点
			Wrapper<ConstantInfo> wrapper = new EntityWrapper<>();
			wrapper.eq("con_key",constantInfo.getConKey());
			wrapper.eq("con_type","0");
			ConstantInfo parentConstant=constantInfoService.selectOne(wrapper);
			Integer count = delFindMapper.findCount("rule_entity_item_info","constant_id",parentConstant.getConId(),"");
			if(count > 0){
				return Result.error(-1,"删除失败，该数据正被其他数据引用");
			}
		}
		constantInfoService.deleteById(id);
		return Result.success(0);
	}





	@GetMapping("getInfoById")
	@ApiOperation(value = "通过id查询详细信息")
	public Result<ConstantInfo> getDateById(Long id) {
		ConstantInfo entityInfo = constantInfoService.selectById(id);
		return Result.success(entityInfo);
	}

	@PostMapping("edit")
	@ApiOperation(value = "新增")
	@Transactional()
	public Result<Integer> edit(ConstantInfo entityInfo) {
		entityInfo.setCreTime(new Date());
		entityInfo.setIsEffect(1);
		entityInfo.setCreUserId(this.getUserId());
		constantInfoService.insertOrUpdate(entityInfo);
		return Result.success(0);
	}
}
