package com.atguigu.wms.base.service;

import com.atguigu.wms.model.base.StorehouseInfo;
import com.atguigu.wms.model.base.StoreshelfInfo;
import com.atguigu.wms.vo.base.StorehouseInfoQueryVo;
import com.atguigu.wms.vo.base.StoreshelfInfoQueryVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

public interface StorehouseInfoService extends IService<StorehouseInfo> {

    /**
     * 分页条件连表查询
     * @param page
     * @param storehouseInfoQueryVo
     * @return
     */
    Page<StorehouseInfo> findPage(Page<StorehouseInfo> page, StorehouseInfoQueryVo storehouseInfoQueryVo);

    /**
     * 新增库区
     * @param storehouseInfo
     * @return
     */
    Boolean insert(StorehouseInfo storehouseInfo);

    /**
     * 更新库区信息
     * @param storehouseInfo
     * @return
     */
    Boolean updateStorehouseInfo(StorehouseInfo storehouseInfo);

    /**
     * 根据id删除
     * @param id
     * @return
     */
    Boolean removeStorehouseInfo(Long id);


    List<StorehouseInfo> findByStoreshelfId(Long storeshelfId);

    String getNameById(Long id);

    List<String> findNameByIdList(List<Long> idList);

}
