package com.atguigu.wms.base.controller;

import com.atguigu.wms.base.service.ShipperInfoService;
import com.atguigu.wms.common.result.Result;
import com.atguigu.wms.model.base.ShipperInfo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: rlk
 * @date: 2022/8/23
 * Description: 货主管理的控制层
 */
@RestController
@RequestMapping("/admin/base/shipperInfo")
public class ShipperInfoController {

    @Autowired
    private ShipperInfoService shipperInfoService;

    @GetMapping("/{pageNum}/{pageSize}")
    public Result findPage(@PathVariable Long pageNum,
                           @PathVariable Long pageSize){
        Page<ShipperInfo> page = new Page<>(pageNum, pageSize);
        shipperInfoService.page(page);
        return Result.ok(page);
    }

}
