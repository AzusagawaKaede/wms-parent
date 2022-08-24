package com.atguigu.wms.base.controller;

import com.atguigu.wms.common.security.AuthContextHolder;
import com.atguigu.wms.model.base.StoreareaInfo;
import com.atguigu.wms.model.base.StorehouseInfo;
import com.atguigu.wms.model.base.StoreshelfInfo;
import com.atguigu.wms.vo.base.StorehouseInfoQueryVo;
import com.atguigu.wms.base.service.StorehouseInfoService;
import com.atguigu.wms.vo.base.StoreshelfInfoQueryVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.atguigu.wms.common.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import javax.annotation.Resource;

/**
 *
 * @author qy
 *
 */
@Api(value = "StorehouseInfo管理", tags = "StorehouseInfo管理")
@RestController
@RequestMapping(value="/admin/base/storehouseInfo")
@SuppressWarnings({"unchecked", "rawtypes"})
public class StorehouseInfoController {
	
	@Resource
	private StorehouseInfoService storehouseInfoService;

	/**
	 * 分页条件连表查询
	 *
	 * @param pageNum
	 * @param pageSize
	 * @param storehouseInfoQueryVo
	 * @return
	 */
	@GetMapping("/{pageNum}/{pageSize}")
	public Result findPage(@PathVariable Long pageNum,
						   @PathVariable Long pageSize,
						   StorehouseInfoQueryVo storehouseInfoQueryVo) {
		Page<StorehouseInfo> page = new Page<>(pageNum, pageSize);
		return Result.ok(storehouseInfoService.findPage(page, storehouseInfoQueryVo));
	}

	/**
	 * 新增库区
	 * @param storehouseInfo
	 * @return
	 */
	@PostMapping("/save")
	public Result save(@RequestBody StorehouseInfo storehouseInfo){
		Boolean insert = storehouseInfoService.insert(storehouseInfo);
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
		return Result.ok(storehouseInfoService.getById(id));
	}

	/**
	 * 跟新库区信息
	 * @param storehouseInfo
	 * @return
	 */
	@PutMapping("/update")
	public Result update(@RequestBody StorehouseInfo storehouseInfo){
		Boolean update = storehouseInfoService.updateStorehouseInfo(storehouseInfo);
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
		Boolean remove = storehouseInfoService.removeStorehouseInfo(id);
		if(remove){
			return Result.ok();
		}else {
			return Result.fail("删除失败");
		}
	}

	@ApiOperation(value = "根据id列表删除")
	@DeleteMapping("batchRemove")
	public Result batchRemove(@RequestBody List<Long> idList) {
		storehouseInfoService.removeByIds(idList);
		return Result.ok();
	}

	@ApiOperation(value = "获取")
	@GetMapping("findByStoreshelfId/{storeshelfId}")
	public Result findByStoreshelfId(@PathVariable Long storeshelfId) {
		return Result.ok(storehouseInfoService.findByStoreshelfId(storeshelfId));
	}

	@ApiOperation(value = "获取对象")
	@GetMapping("getStorehouseInfo/{id}")
	public StorehouseInfo getStorehouseInfo(@PathVariable Long id) {
		return storehouseInfoService.getById(id);
	}

	@ApiOperation(value = "获取名称")
	@GetMapping("getNameById/{id}")
	public String getNameById(@PathVariable Long id) {
		return storehouseInfoService.getNameById(id);
	}

	@PostMapping("findNameByIdList")
	public List<String> findNameByIdList(@RequestBody List<Long> idList) {
		return storehouseInfoService.findNameByIdList(idList);
	}
}

