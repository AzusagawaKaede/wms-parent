package com.atguigu.wms.base.mapper;

import com.atguigu.wms.model.base.ShipperInfo;
import com.atguigu.wms.vo.base.ShipperInfoQueryVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
 * @author: rlk
 * @date: 2022/8/23
 * Description: 货主管理的mapper
 */
@Mapper
public interface ShipperInfoMapper extends BaseMapper<ShipperInfo> {
    /**
     * 分页条件连表查询
     * @param page
     * @param shipperInfoQueryVo
     * @return
     */
    Page<ShipperInfo> findPage(Page<ShipperInfo> page,
                               @Param("shipperInfoQueryVo") ShipperInfoQueryVo shipperInfoQueryVo);

    /**
     * 根据id连表查询货主信息
     * @param id
     * @return
     */
    ShipperInfo get(Long id);
}
