package com.atguigu.wms.base.mapper;

import com.atguigu.wms.model.base.ShipperInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author: rlk
 * @date: 2022/8/23
 * Description: 货主管理的mapper
 */
@Mapper
public interface ShipperInfoMapper extends BaseMapper<ShipperInfo> {
}
