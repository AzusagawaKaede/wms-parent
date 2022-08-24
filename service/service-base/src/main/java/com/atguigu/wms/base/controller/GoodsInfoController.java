package com.atguigu.wms.base.controller;

import com.atguigu.wms.model.base.GoodsInfo;
import com.atguigu.wms.model.inventory.InventoryInfo;
import com.atguigu.wms.vo.PageVo;
import com.atguigu.wms.vo.base.GoodsInfoQueryVo;
import com.atguigu.wms.base.service.GoodsInfoService;
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
 * @author qy
 */
@Api(value = "GoodsInfo管理", tags = "GoodsInfo管理")
@RestController
@RequestMapping(value = "/admin/base/goodsInfo")
@SuppressWarnings({"unchecked", "rawtypes"})
public class GoodsInfoController {

    @Resource
    private GoodsInfoService goodsInfoService;

    /**
     * 分页条件查询
     *
     * @param pageNum
     * @param pageSize
     * @param goodsInfoQueryVo
     * @return
     */
    @GetMapping("/{pageNum}/{pageSize}")
    public Result findPage(@PathVariable Long pageNum,
                           @PathVariable Long pageSize,
                           GoodsInfoQueryVo goodsInfoQueryVo) {
        Page<GoodsInfo> page = new Page<>(pageNum, pageSize);
        goodsInfoService.findPage(page, goodsInfoQueryVo);
        return Result.ok(page);
    }

    /**
     * 新增货品关联
     *
     * @param goodsInfo
     * @return
     */
    @PostMapping("/save")
    public Result save(@RequestBody GoodsInfo goodsInfo) {
        Boolean insert = goodsInfoService.insert(goodsInfo);
        if (insert) {
            return Result.ok();
        } else {
            return Result.fail("SKU已被关联");
        }
    }

    /**
     * 根据id获取货品
     *
     * @param id
     * @return
     */
    @GetMapping("/get/{id}")
    public Result get(@PathVariable Long id) {
        return Result.ok(goodsInfoService.get(id));
    }

    /**
     * 更新货品
     *
     * @param goodsInfo
     * @return
     */
    @PutMapping("/update")
    public Result update(@RequestBody GoodsInfo goodsInfo) {
        Boolean update = goodsInfoService.updateGoodsInfo(goodsInfo);
        if (update) {
            return Result.ok();
        }else {
        	return Result.fail("更新失败");
		}
    }

	/**
	 * 根据id删除货品
	 * @param id
	 * @return
	 */
	@DeleteMapping("/remove/{id}")
	public Result remove(@PathVariable Long id){
		Boolean remove = goodsInfoService.removeGoodsInfo(id);
		if(remove){
			return Result.ok();
		}else {
			return Result.fail("删除失败");
		}
	}

	@GetMapping("/updateStatus/{id}/{status}")
	public Result updateStatus(@PathVariable Long id,
							   @PathVariable Integer status){
		Boolean update = goodsInfoService.updateStatus(id, status);
		if(update){
			return Result.ok();
		}else {
			return Result.fail("更新失败");
		}
	}

    @ApiOperation(value = "根据关键字查看")
    @GetMapping("findByKeyword/{keyword}")
    public Result findByKeyword(@PathVariable String keyword) {
        return Result.ok(goodsInfoService.findByKeyword(keyword));
    }

    @ApiOperation(value = "根据第三级分类id获取货品三级分类id列表")
    @GetMapping("findGoodsTypeIdList/{goodsTypeId}")
    public Result findGoodsTypeIdList(@PathVariable Long goodsTypeId) {
        return Result.ok(goodsInfoService.findGoodsTypeIdList(goodsTypeId));
    }

    @ApiOperation(value = "获取列表")
    @PostMapping("findList")
    public List<GoodsInfo> findList(@RequestBody List<Long> idList) {
        return goodsInfoService.listByIds(idList);
    }


//	@ApiOperation(value = "获取分页列表")
//	@PostMapping("findPage/{page}/{limit}")
//	public PageVo<GoodsInfo> findPage(
//			@ApiParam(name = "page", value = "当前页码", required = true)
//			@PathVariable Long page,
//
//			@ApiParam(name = "limit", value = "每页记录数", required = true)
//			@PathVariable Long limit,
//
//			@ApiParam(name = "goodsInfoVo", value = "查询对象", required = false)
//					@RequestBody GoodsInfoQueryVo goodsInfoQueryVo) {
//		Page<GoodsInfo> pageParam = new Page<>(page, limit);
//		//IPage<GoodsInfo> pageModel = goodsInfoService.selectPage(pageParam, goodsInfoQueryVo);
//		return new PageVo<>(null);
//	}

    @ApiOperation(value = "获取")
    @GetMapping("getGoodsInfo/{id}")
    public GoodsInfo getGoodsInfo(@PathVariable Long id) {
        return goodsInfoService.getGoodsInfo(id);
    }

    @ApiOperation(value = "获取")
    @GetMapping("getGoodsInfoBySkuId/{skuId}")
    public GoodsInfo getGoodsInfoBySkuId(@PathVariable Long skuId) {
        return goodsInfoService.getGoodsInfoBySkuId(skuId);
    }
}

