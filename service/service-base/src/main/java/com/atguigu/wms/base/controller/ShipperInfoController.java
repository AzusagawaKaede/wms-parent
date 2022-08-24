package com.atguigu.wms.base.controller;

import com.atguigu.wms.base.service.ShipperInfoService;
import com.atguigu.wms.common.result.Result;
import com.atguigu.wms.model.base.ShipperInfo;
import com.atguigu.wms.vo.base.ShipperInfoQueryVo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

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

    /**
     * 分页条件查询货主列表
     * @param pageNum
     * @param pageSize
     * @param shipperInfoQueryVo
     * @return
     */
    @GetMapping("/{pageNum}/{pageSize}")
    public Result findPage(@PathVariable Long pageNum,
                           @PathVariable Long pageSize,
                           ShipperInfoQueryVo shipperInfoQueryVo){
        Page<ShipperInfo> page = new Page<>(pageNum, pageSize);
        shipperInfoService.findPage(page, shipperInfoQueryVo);
        return Result.ok(page);
    }

    /**
     * 新增货主信息
     * @param shipperInfo
     * @return
     */
    @PostMapping("/save")
    public Result save(@RequestBody ShipperInfo shipperInfo){
        if(shipperInfo == null){
            return Result.fail("参数错误");
        }
        boolean save = shipperInfoService.save(shipperInfo);
        if(save){
            return Result.ok();
        }else {
            return Result.fail("新增失败");
        }
    }

    /**
     * 修改货主信息回显
     * @param id
     * @return
     */
    @GetMapping("/get/{id}")
    public Result get(@PathVariable Long id){
        ShipperInfo shipperInfo = shipperInfoService.get(id);
        return Result.ok(shipperInfo);
    }

    /**
     * 更新货主信息
     * @param shipperInfo
     * @return
     */
    @PutMapping("/update")
    public Result update(@RequestBody ShipperInfo shipperInfo){
        if(shipperInfo == null || shipperInfo.getId() == null){
            return Result.fail("参数错误");
        }
        boolean update = shipperInfoService.updateById(shipperInfo);
        if(update){
            return Result.ok();
        }else {
            return Result.fail("更新失败");
        }
    }

    /**
     * 根据Id删除货主信息
     * @param id
     * @return
     */
    @DeleteMapping("/remove/{id}")
    public Result remove(@PathVariable Long id){
        boolean remove = shipperInfoService.removeById(id);
        if(remove){
            return Result.ok();
        }else {
            return Result.fail("删除失败");
        }
    }

}
