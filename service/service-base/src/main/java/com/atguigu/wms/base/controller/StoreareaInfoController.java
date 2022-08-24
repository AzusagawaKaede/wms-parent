package com.atguigu.wms.base.controller;

import com.atguigu.wms.common.security.AuthContextHolder;
import com.atguigu.wms.model.base.StoreareaInfo;
import com.atguigu.wms.model.base.WarehouseInfo;
import com.atguigu.wms.vo.base.StoreareaInfoQueryVo;
import com.atguigu.wms.base.service.StoreareaInfoService;
import com.atguigu.wms.vo.base.WarehouseInfoQueryVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.atguigu.wms.common.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import javax.annotation.Resource;

/**
 *
 * @author qy
 *
 */
@Api(value = "StoreareaInfo管理", tags = "StoreareaInfo管理")
@RestController
@RequestMapping(value="/admin/base/storeareaInfo")
@SuppressWarnings({"unchecked", "rawtypes"})
public class StoreareaInfoController {
	
	@Resource
	private StoreareaInfoService storeareaInfoService;

	/**
	 * 分页条件连表查询
	 *
	 * @param pageNum
	 * @param pageSize
	 * @param storeareaInfoQueryVo
	 * @return
	 */
	@GetMapping("/{pageNum}/{pageSize}")
	public Result findPage(@PathVariable Long pageNum,
						   @PathVariable Long pageSize,
						   StoreareaInfoQueryVo storeareaInfoQueryVo) {
		Page<StoreareaInfo> page = new Page<>(pageNum, pageSize);
		return Result.ok(storeareaInfoService.findPage(page, storeareaInfoQueryVo));
	}

	/**
	 * 新增库区
	 * @param storeareaInfo
	 * @return
	 */
	@PostMapping("/save")
	public Result save(@RequestBody StoreareaInfo storeareaInfo){
		Boolean insert = storeareaInfoService.insert(storeareaInfo);
		if(insert){
			return Result.ok();
		}else{
			return Result.fail("新增失败");
		}
	}

	/**
	 * 根据id查询库区信息
	 * @param id
	 * @return
	 */
	@GetMapping("/get/{id}")
	public Result get(@PathVariable Long id){
		return Result.ok(storeareaInfoService.getById(id));
	}

	/**
	 * 跟新库区信息
	 * @param storeareaInfo
	 * @return
	 */
	@PutMapping("/update")
	public Result update(@RequestBody StoreareaInfo storeareaInfo){
		Boolean update = storeareaInfoService.updateStoreareaInfo(storeareaInfo);
		if (update) {
			return Result.ok();
		}else {
			return Result.fail("更新失败");
		}
	}

	/**
	 * 根据id删除
	 * @param id
	 * @return
	 */
	@DeleteMapping("/remove/{id}")
	public Result delete(@PathVariable Long id){
		Boolean remove = storeareaInfoService.removeStoreareaInfo(id);
		if(remove){
			return Result.ok();
		}else {
			return Result.fail("删除失败");
		}
	}

	/**
	 * 根据仓库id查询所有的库区
	 * @param id
	 * @return
	 */
	@GetMapping("/findByWarehouseId/{id}")
	public Result findByWarehouseId(@PathVariable Long id){
		return Result.ok(storeareaInfoService.findByWarehouseId(id));
	}

	@ApiOperation(value = "获取对象")
	@GetMapping("getStoreareaInfo/{id}")
	public StoreareaInfo getStoreareaInfo(@PathVariable Long id) {
		return storeareaInfoService.getById(id);
	}

	@ApiOperation(value = "获取名称")
	@GetMapping("getNameById/{id}")
	public String getNameById(@PathVariable Long id) {
		return storeareaInfoService.getNameById(id);
	}
}

