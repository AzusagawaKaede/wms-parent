package com.atguigu.wms.base.service;

import com.atguigu.wms.model.base.WarehouseInfo;
import com.atguigu.wms.vo.base.WarehouseInfoQueryVo;
import com.atguigu.wms.vo.outbound.OutOrderAddressVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface WarehouseInfoService extends IService<WarehouseInfo> {

    /**
     * 分页条件连表查询warehouseInfo
     * @param page
     * @param warehouseInfoQueryVo
     * @return
     */
    Page<WarehouseInfo> findPage(Page<WarehouseInfo> page, WarehouseInfoQueryVo warehouseInfoQueryVo);

    /**
     * 根据id删除仓库
     * @param id
     * @return
     */
    boolean removeWarehouse(Long id);

    /**
     * 货区-1
     * @param warehouseId
     * @return
     */
    int decrease(Long warehouseId);

    /**
     * 货区+1
     * @param warehouseId
     * @return
     */
    int addStorearea(Long warehouseId);

    String getNameById(Long id);

    List<Map<String, Object>> findNodes();

    List<String> findNameByIdList(List<Long> idSet);

    List<Long> findPriorityWarehouseIdList(OutOrderAddressVo outOrderAddressVo);

}
