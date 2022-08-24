package com.atguigu.wms.base.mapper;

import com.atguigu.wms.model.base.WarehouseInfo;
import com.atguigu.wms.vo.base.WarehouseInfoQueryVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface WarehouseInfoMapper extends BaseMapper<WarehouseInfo> {

    /**
     * 分页连表条件查询WarehouseInfo
     * @param page
     * @param warehouseInfoQueryVo
     * @return
     */
    Page<WarehouseInfo> findPage(Page<WarehouseInfo> page, @Param("warehouseInfoQueryVo") WarehouseInfoQueryVo warehouseInfoQueryVo);
}
