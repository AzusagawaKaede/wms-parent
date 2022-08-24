package com.atguigu.wms.base.controller;

import com.atguigu.wms.base.service.StoreshelfInfoService;
import com.atguigu.wms.model.base.StoreshelfInfo;
import com.atguigu.wms.vo.base.StoreareaInfoQueryVo;
import com.atguigu.wms.vo.base.StoreshelfInfoQueryVo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.atguigu.wms.common.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import javax.annotation.Resource;

/**
 *
 * @author qy
 *
 */
@Api(value = "StoreshelfInfo管理", tags = "StoreshelfInfo管理")
@RestController
@RequestMapping(value="/admin/base/storeshelfInfo")
@SuppressWarnings({"unchecked", "rawtypes"})
public class StoreshelfInfoController {
	
	@Resource
	private StoreshelfInfoService storeshelfInfoService;

	/**
	 * 分页条件连表查询
	 *
	 * @param pageNum
	 * @param pageSize
	 * @param storeshelfInfoQueryVo
	 * @return
	 */
	@GetMapping("/{pageNum}/{pageSize}")
	public Result findPage(@PathVariable Long pageNum,
						   @PathVariable Long pageSize,
						   StoreshelfInfoQueryVo storeshelfInfoQueryVo) {
		Page<StoreshelfInfo> page = new Page<>(pageNum, pageSize);
		return Result.ok(storeshelfInfoService.findPage(page, storeshelfInfoQueryVo));
	}

	/**
	 * 新增库区
	 * @param storeareaInfo
	 * @return
	 */
	@PostMapping("/save")
	public Result save(@RequestBody StoreshelfInfo storeareaInfo){
		Boolean insert = storeshelfInfoService.insert(storeareaInfo);
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
		return Result.ok(storeshelfInfoService.getById(id));
	}

	/**
	 * 跟新库区信息
	 * @param storeareaInfo
	 * @return
	 */
	@PutMapping("/update")
	public Result update(@RequestBody StoreshelfInfo storeareaInfo){
		Boolean update = storeshelfInfoService.updateStoreshelfInfo(storeareaInfo);
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
		Boolean remove = storeshelfInfoService.removeStoreshelfInfo(id);
		if(remove){
			return Result.ok();
		}else {
			return Result.fail("删除失败");
		}
	}

	/**
	 * 根据id查询货架
	 * @param id
	 * @return
	 */
	@GetMapping("/findByStoreareaId/{id}")
	public Result findByStoreareaId(@PathVariable Long id){
		return Result.ok(storeshelfInfoService.findByStoreareaId(id));
	}

	@ApiOperation(value = "根据id列表删除")
	@DeleteMapping("batchRemove")
	public Result batchRemove(@RequestBody List<Long> idList) {
		storeshelfInfoService.removeByIds(idList);
		return Result.ok();
	}

	@ApiOperation(value = "获取对象")
	@GetMapping("getStoreshelfInfo/{id}")
	public StoreshelfInfo getStoreshelfInfo(@PathVariable Long id) {
		return storeshelfInfoService.getById(id);
	}

	@ApiOperation(value = "获取名称")
	@GetMapping("getNameById/{id}")
	public String getNameById(@PathVariable Long id) {
		return storeshelfInfoService.getNameById(id);
	}
}

