package com.atguigu.wms.base.controller;

import com.atguigu.wms.base.service.DictService;
import com.atguigu.wms.base.service.GoodsTypeService;
import com.atguigu.wms.common.result.Result;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 *
 * @author qy
 *
 */
@Api(value = "GoodsType管理", tags = "GoodsType管理")
@RestController
@RequestMapping(value="/admin/base/goodsType")
@SuppressWarnings({"unchecked", "rawtypes"})
public class GoodsTypeController {

    @Autowired
    private GoodsTypeService goodsTypeService;

    /**
     * 根据父Id查询子节点GoodsType
     * @param parentId
     * @return
     */
    @GetMapping("/findByParentId/{parentId}")
    public Result findByParentId(@PathVariable Long parentId){
        return Result.ok(goodsTypeService.findByParentId(parentId));
    }

}

