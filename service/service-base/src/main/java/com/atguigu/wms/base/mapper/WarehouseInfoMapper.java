package com.atguigu.wms.base.mapper;

import com.atguigu.wms.model.base.WarehouseInfo;
import com.atguigu.wms.vo.base.WarehouseInfoQueryVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;
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

    /**
     * 新增库区
     * @param warehouseId
     * @return
     */
    @Update("update warehouse_info set storearea_count = storearea_count + 1 where id = #{warehouseId}")
    int addStorearea(Long warehouseId);

    /**
     * 减少库区
     * @param warehouseId
     * @return
     */
    @Update("update warehouse_info set storearea_count = storearea_count - 1 where id = #{warehouseId}")
    int decrease(Long warehouseId);

    /**
     * 新增库区
     * @param warehouseId
     * @return
     */
    @Update("update warehouse_info set storeshelf_count = storeshelf_count + 1 where id = #{warehouseId}")
    int addStoreshelf(Long warehouseId);

    /**
     * 减少库区
     * @param warehouseId
     * @return
     */
    @Update("update warehouse_info set storeshelf_count = storeshelf_count - 1 where id = #{warehouseId}")
    int decreaseStoreshelf(Long warehouseId);
}
