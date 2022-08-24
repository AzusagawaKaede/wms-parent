package com.atguigu.wms.base.controller;

import com.atguigu.wms.common.security.AuthContextHolder;
import com.atguigu.wms.model.base.StoreshelfInfo;
import com.atguigu.wms.model.base.WarehouseInfo;
import com.atguigu.wms.vo.base.WarehouseInfoQueryVo;
import com.atguigu.wms.base.service.WarehouseInfoService;
import com.atguigu.wms.vo.outbound.OutOrderAddressVo;
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
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.annotation.Resource;

/**
 * @author qy
 */
@Api(value = "WarehouseInfo管理", tags = "WarehouseInfo管理")
@RestController
@RequestMapping(value = "/admin/base/warehouseInfo")
@SuppressWarnings({"unchecked", "rawtypes"})
public class WarehouseInfoController {

    @Resource
    private WarehouseInfoService warehouseInfoService;

    /**
     * 分页条件连表查询
     *
     * @param pageNum
     * @param pageSize
     * @param warehouseInfoQueryVo
     * @return
     */
    @GetMapping("/{pageNum}/{pageSize}")
    public Result findPage(@PathVariable Long pageNum,
                           @PathVariable Long pageSize,
                           @RequestParam(required = false) WarehouseInfoQueryVo warehouseInfoQueryVo) {
        Page<WarehouseInfo> page = new Page<>(pageNum, pageSize);
        return Result.ok(warehouseInfoService.findPage(page, warehouseInfoQueryVo));
    }

    /**
     * 新增仓库
     *
     * @param warehouseInfo
     * @return
     */
    @PostMapping("/save")
    public Result save(@RequestBody WarehouseInfo warehouseInfo) {
        if (warehouseInfo == null) {
            return Result.fail("参数错误");
        }
        warehouseInfo.setStoreareaCount(0);
        warehouseInfo.setStoreshelfCount(0);
        warehouseInfo.setStorehouseCount(0);
        warehouseInfo.setCreateId(1L);
        warehouseInfo.setCreateName("admin");
        warehouseInfo.setUpdateId(1L);
        warehouseInfo.setUpdateName("admin");
        warehouseInfo.setCreateTime(new Date());
        warehouseInfo.setUpdateTime(new Date());
        warehouseInfo.setIsDeleted(0);
        boolean save = warehouseInfoService.save(warehouseInfo);
        if (save) {
            return Result.ok();
        } else {
            return Result.fail("新增失败");
        }
    }

    /**
     * 根据id查询仓库信息
     *
     * @param id
     * @return
     */
    @GetMapping("/get/{id}")
    public Result get(@PathVariable Long id) {
        return Result.ok(warehouseInfoService.getById(id));
    }

    /**
     * 更新仓库信息
     *
     * @param warehouseInfo
     * @return
     */
    @PutMapping("/update")
    public Result update(@RequestBody WarehouseInfo warehouseInfo) {
        if (warehouseInfo == null || warehouseInfo.getId() == null) {
            return Result.fail("参数错误");
        }
        warehouseInfo.setUpdateId(1L);
        warehouseInfo.setUpdateName("admin");
        warehouseInfo.setUpdateTime(new Date());
        boolean update = warehouseInfoService.updateById(warehouseInfo);
        if (update) {
            return Result.ok();
        } else {
            return Result.fail("更新失败");
        }
    }

	/**
	 * 根据id删除仓库信息
	 * @param id
	 * @return
	 */
	@DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable Long id) {
        boolean remove = warehouseInfoService.removeById(id);
        if (remove) {
			return Result.ok();
        }else {
        	return Result.fail("删除失败");
		}
    }

    @ApiOperation(value = "根据id列表删除")
    @DeleteMapping("batchRemove")
    public Result batchRemove(@RequestBody List<Long> idList) {
        warehouseInfoService.removeByIds(idList);
        return Result.ok();
    }

    @ApiOperation(value = "查询库位节点")
    @GetMapping("findNodes")
    public Result findNodes() {
        return Result.ok(warehouseInfoService.findNodes());
    }

    @ApiOperation(value = "获取对象")
    @GetMapping("getWarehouseInfo/{id}")
    public WarehouseInfo getWarehouseInfo(@PathVariable Long id) {
        return warehouseInfoService.getById(id);
    }

    @ApiOperation(value = "获取名称")
    @GetMapping("getNameById/{id}")
    public String getNameById(@PathVariable Long id) {
        return warehouseInfoService.getNameById(id);
    }

    @PostMapping("findNameByIdList")
    public List<String> findNameByIdList(@RequestBody List<Long> idList) {
        return warehouseInfoService.findNameByIdList(idList);
    }

    @ApiOperation(value = "根据用户地址给满足条件的仓库指定优先级")
    @PostMapping("findPriorityWarehouseIdList")
    public List<Long> findNameByIdList(@RequestBody OutOrderAddressVo outOrderAddressVo) {
        return warehouseInfoService.findPriorityWarehouseIdList(outOrderAddressVo);
    }
}

