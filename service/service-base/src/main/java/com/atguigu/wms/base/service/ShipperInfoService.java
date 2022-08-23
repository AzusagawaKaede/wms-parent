package com.atguigu.wms.base.service;

import com.atguigu.wms.model.base.ShipperInfo;
import com.atguigu.wms.vo.base.ShipperInfoQueryVo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * @author: rlk
 * @date: 2022/8/23
 * Description: 货主管理的业务层
 */
public interface ShipperInfoService extends IService<ShipperInfo> {

    /**
     * 分页条件查询货主列表
     * @param page
     * @param shipperInfoQueryVo
     * @return
     */
    Page<ShipperInfo> findPage(Page<ShipperInfo> page, ShipperInfoQueryVo shipperInfoQueryVo);

    /**
     * 根据Id连表查询货主信息
     * @param id
     * @return
     */
    ShipperInfo get(Long id);
}
