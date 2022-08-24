package com.atguigu.wms.base.service;

import com.atguigu.wms.model.base.GoodsType;
import com.atguigu.wms.vo.base.GoodsTypeQueryVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

public interface GoodsTypeService extends IService<GoodsType> {

    String getNameById(Long id);

    /**
     * 根据父Id查询子节点GoodsType
     * @param parentId
     * @return
     */
    List<GoodsType> findByParentId(Long parentId);

    /**
     * 查询一二三三级分类
     * @return
     */
    List<GoodsType> findNodes();
}