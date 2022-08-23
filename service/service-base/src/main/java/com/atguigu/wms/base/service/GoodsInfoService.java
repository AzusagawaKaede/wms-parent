package com.atguigu.wms.base.service;

import com.atguigu.wms.model.base.GoodsInfo;
import com.atguigu.wms.vo.base.GoodsInfoQueryVo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface GoodsInfoService extends IService<GoodsInfo> {

    /**
     * 分页条件查询
     * @param page
     * @param goodsInfoQueryVo
     * @return
     */
    Page<GoodsInfo> findPage(Page<GoodsInfo> page, GoodsInfoQueryVo goodsInfoQueryVo);

    List<GoodsInfo> findByKeyword(String keyword);

    GoodsInfo getGoodsInfo(Long id);

    GoodsInfo getGoodsInfoBySkuId(Long skuId);

    List<String> findGoodsTypeIdList(Long goodsTypeId);

}
