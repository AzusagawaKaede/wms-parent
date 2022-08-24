package com.atguigu.wms.base.mapper;

import com.atguigu.wms.model.base.GoodsInfo;
import com.atguigu.wms.vo.base.GoodsInfoQueryVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface GoodsInfoMapper extends BaseMapper<GoodsInfo> {

    /**
     * 分页连表条件查询
     * @param page
     * @param goodsInfoQueryVo
     * @return
     */
    Page<GoodsInfo> findPage(Page<GoodsInfo> page,
                             @Param("goodsInfoQueryVo") GoodsInfoQueryVo goodsInfoQueryVo);

    /**
     * 根据id查询货品
     * @param id
     * @return
     */
    GoodsInfo get(Long id);

    /**
     * 更新货品启用和下线状态
     * @param id
     * @param status
     * @return
     */
    @Update("update goods_info set status = #{status} where id = #{id}")
    Boolean updateStatus(@Param("id") Long id, @Param("status") Integer status);
}
