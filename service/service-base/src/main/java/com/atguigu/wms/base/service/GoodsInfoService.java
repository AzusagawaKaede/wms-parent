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

    /**
     * 新增货品
     * @param goodsInfo
     * @return
     */
    Boolean insert(GoodsInfo goodsInfo);

    /**
     * 根据id查询货品
     * @param id
     * @return
     */
    GoodsInfo get(Long id);

    /**
     * 更新货品
     * @param goodsInfo
     * @return
     */
    Boolean updateGoodsInfo(GoodsInfo goodsInfo);

    /**
     * 根据id删除GoodsInfo
     * @param id
     * @return
     */
    Boolean removeGoodsInfo(Long id);

    /**
     * 根据id和status修改启用和下线状态
     * @param id
     * @param status
     * @return
     */
    Boolean updateStatus(Long id, Integer status);

    List<GoodsInfo> findByKeyword(String keyword);

    GoodsInfo getGoodsInfo(Long id);

    GoodsInfo getGoodsInfoBySkuId(Long skuId);

    List<String> findGoodsTypeIdList(Long goodsTypeId);
}
