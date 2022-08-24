package com.atguigu.wms.base.service;

import com.atguigu.wms.model.base.StoreareaInfo;
import com.atguigu.wms.model.base.WarehouseInfo;
import com.atguigu.wms.vo.base.StoreareaInfoQueryVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

public interface StoreareaInfoService extends IService<StoreareaInfo> {

    /**
     * 分页条件连表查询
     * @param page
     * @param storeareaInfoQueryVo
     * @return
     */
    Page<StoreareaInfo> findPage(Page<StoreareaInfo> page, StoreareaInfoQueryVo storeareaInfoQueryVo);

    /**
     * 新增库区
     * @param storeareaInfo
     * @return
     */
    Boolean insert(StoreareaInfo storeareaInfo);

    /**
     * 更新库区信息
     * @param storeareaInfo
     * @return
     */
    Boolean updateStoreareaInfo(StoreareaInfo storeareaInfo);

    /**
     * 根据id删除
     * @param id
     * @return
     */
    Boolean removeStoreareaInfo(Long id);

    /**
     * +1
     * @param storeareaId
     * @return
     */
    int addStoreshlef(Long storeareaId);

    String getNameById(Long id);

    /**
     * +1
     * @param storeareaId
     * @return
     */
    int decrease(Long storeareaId);

    /**
     * 根据仓库查询库区
     * @param id
     * @return
     */
    List<WarehouseInfo> findByWarehouseId(Long id);

}
