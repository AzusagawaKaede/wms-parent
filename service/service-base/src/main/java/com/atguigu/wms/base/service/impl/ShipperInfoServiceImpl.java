package com.atguigu.wms.base.service.impl;

import com.atguigu.wms.base.mapper.ShipperInfoMapper;
import com.atguigu.wms.base.service.ShipperInfoService;
import com.atguigu.wms.model.base.ShipperInfo;
import com.atguigu.wms.vo.base.ShipperInfoQueryVo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @author: rlk
 * @date: 2022/8/23
 * Description: 货主管理的业务层实现类
 */
@Service
public class ShipperInfoServiceImpl extends ServiceImpl<ShipperInfoMapper, ShipperInfo> implements ShipperInfoService{

    @Resource
    private ShipperInfoMapper shipperInfoMapper;

    /**
     * 分页条件查询货主列表
     *
     * @param page
     * @param shipperInfoQueryVo
     * @return
     */
    @Override
    public Page<ShipperInfo> findPage(Page<ShipperInfo> page, ShipperInfoQueryVo shipperInfoQueryVo) {
        return shipperInfoMapper.findPage(page, shipperInfoQueryVo);
    }

    /**
     * 根据Id连表查询货主信息
     *
     * @param id
     * @return
     */
    @Override
    public ShipperInfo get(Long id) {
        if(id == null){
            throw new RuntimeException("参数错误");
        }
        return shipperInfoMapper.get(id);
    }
}
