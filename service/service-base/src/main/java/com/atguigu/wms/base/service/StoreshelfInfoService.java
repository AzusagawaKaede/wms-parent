package com.atguigu.wms.base.service;

import com.atguigu.wms.model.base.StoreareaInfo;
import com.atguigu.wms.model.base.StoreshelfInfo;
import com.atguigu.wms.vo.base.StoreareaInfoQueryVo;
import com.atguigu.wms.vo.base.StoreshelfInfoQueryVo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;


public interface StoreshelfInfoService extends IService<StoreshelfInfo> {

    /**
     * 分页条件连表查询
     * @param page
     * @param storeshelfInfoQueryVo
     * @return
     */
    Page<StoreshelfInfo> findPage(Page<StoreshelfInfo> page, StoreshelfInfoQueryVo storeshelfInfoQueryVo);

    /**
     * 新增库区
     * @param storeshelfInfo
     * @return
     */
    Boolean insert(StoreshelfInfo storeshelfInfo);

    /**
     * 更新库区信息
     * @param storeshelfInfo
     * @return
     */
    Boolean updateStoreshelfInfo(StoreshelfInfo storeshelfInfo);

    /**
     * 根据id删除
     * @param id
     * @return
     */
    Boolean removeStoreshelfInfo(Long id);

    String getNameById(Long id);

}
