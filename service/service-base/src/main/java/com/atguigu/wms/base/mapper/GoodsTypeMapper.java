package com.atguigu.wms.base.mapper;

import com.atguigu.wms.model.base.GoodsType;
import com.atguigu.wms.vo.base.GoodsTypeQueryVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface GoodsTypeMapper extends BaseMapper<GoodsType> {

    /**
     * 根据Id查询子节点GoodType
     * @param parentId
     * @return
     */
    List<GoodsType> findByParentId(Long parentId);
}
