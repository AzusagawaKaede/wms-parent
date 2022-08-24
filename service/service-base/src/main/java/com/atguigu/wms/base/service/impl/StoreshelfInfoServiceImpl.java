package com.atguigu.wms.base.service.impl;

import com.atguigu.wms.base.service.*;
import com.atguigu.wms.model.base.StoreshelfInfo;
import com.atguigu.wms.model.base.StoreshelfInfo;
import com.atguigu.wms.model.base.WarehouseInfo;
import com.atguigu.wms.vo.base.StoreareaInfoQueryVo;
import com.atguigu.wms.vo.base.StoreshelfInfoQueryVo;
import com.atguigu.wms.vo.base.StoreshelfInfoQueryVo;
import com.atguigu.wms.base.mapper.StoreshelfInfoMapper;
import com.atguigu.wms.base.service.StoreshelfInfoService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.*;

@Service
@SuppressWarnings({"unchecked", "rawtypes"})
public class StoreshelfInfoServiceImpl extends ServiceImpl<StoreshelfInfoMapper, StoreshelfInfo> implements StoreshelfInfoService {

    @Autowired
    private WarehouseInfoService warehouseInfoService;
    @Autowired
    private StoreshelfInfoMapper storeshelfInfoMapper;
    @Autowired
    private StoreareaInfoService storeareaInfoService;

    /**
     * 分页条件连表查询
     *
     * @param page
     * @param storeshelfInfoQueryVo
     * @return
     */
    @Override
    public Page<StoreshelfInfo> findPage(Page<StoreshelfInfo> page, StoreshelfInfoQueryVo storeshelfInfoQueryVo) {
        if (storeshelfInfoQueryVo == null) {
            storeshelfInfoQueryVo = new StoreshelfInfoQueryVo();
        }
        return storeshelfInfoMapper.findPage(page, storeshelfInfoQueryVo);
    }

    /**
     * 货架
     *
     * @param storeshelfInfo
     * @return
     */
    @Override
    public Boolean insert(StoreshelfInfo storeshelfInfo) {
        if (storeshelfInfo == null) {
            throw new RuntimeException("参数错误");
        }
        //新增
        storeshelfInfo.setStorehouseCount(0);
        storeshelfInfo.setCreateId(1L);
        storeshelfInfo.setCreateName("admin");
        storeshelfInfo.setUpdateId(1L);
        storeshelfInfo.setUpdateName("admin");
        storeshelfInfo.setCreateTime(new Date());
        storeshelfInfo.setUpdateTime(new Date());
        storeshelfInfo.setIsDeleted(0);
        int insert = storeshelfInfoMapper.insert(storeshelfInfo);
        if (insert <= 0) {
            throw new RuntimeException("新增失败");
        }
        //给仓库+1
        int update = warehouseInfoService.addStoreshelf(storeshelfInfo.getWarehouseId());
        //给库区+1
        int update2 = storeareaInfoService.addStoreshlef(storeshelfInfo.getStoreareaId());
        if (update <= 0 || update2 <= 0) {
            throw new RuntimeException("新增失败");
        }
        return true;
    }

    /**
     * 更新库区信息
     *
     * @param storeareaInfo
     * @return
     */
    @Override
    public Boolean updateStoreshelfInfo(StoreshelfInfo storeareaInfo) {
        //参数校验
        if (storeareaInfo == null || storeareaInfo.getId() == null) {
            throw new RuntimeException("参数错误");
        }
        //修改更新时间
        storeareaInfo.setUpdateId(1L);
        storeareaInfo.setUpdateName("admin");
        storeareaInfo.setUpdateTime(new Date());
        //查询原来的info，判断仓库id是否一致
        StoreshelfInfo info = storeshelfInfoMapper.selectById(storeareaInfo.getId());
        //仓库-1
        int decrease = warehouseInfoService.decreaseStoreshelf(info.getWarehouseId());
        //库区-1
        int decrease2 = storeareaInfoService.decrease(info.getStoreareaId());
        //新增
        int update = storeshelfInfoMapper.updateById(storeareaInfo);
        //仓库+1
        int add = warehouseInfoService.addStoreshelf(storeareaInfo.getWarehouseId());
        //库区+1
        int add2 = storeareaInfoService.addStoreshlef(storeareaInfo.getStoreareaId());
        if (decrease < 0 || decrease2 < 0 ||
				update < 0 || add < 0 || add2 < 0) {
            throw new RuntimeException("更新失败");
        }
        return true;
    }

    /**
     * 根据id删除
     *
     * @param id
     * @return
     */
    @Override
    public Boolean removeStoreshelfInfo(Long id) {
        //参数校验
        if (id == null) {
            throw new RuntimeException("参数错误");
        }
        //先查询货架数和库位数，非0不允许删除
        StoreshelfInfo storeareaInfo = storeshelfInfoMapper.selectById(id);
        //判断是否存在
        if (storeareaInfo != null && storeareaInfo.getStorehouseCount() != 0) {
            throw new RuntimeException("库存数不为0，不可删除");
        }
        //为0则删除
        int delete = storeshelfInfoMapper.deleteById(id);
        //删除仓库的数量
        int delete2 = warehouseInfoService.decrease(storeareaInfo.getWarehouseId());
        //删除库区的数量
		int delete3 = storeareaInfoService.decrease(storeareaInfo.getStoreareaId());
        if (delete <= 0 || delete2 <= 0 || delete3 <= 0) {
            throw new RuntimeException("删除失败");
        }
        return true;
    }

    @Cacheable(value = "storeshelfInfo", keyGenerator = "keyGenerator")
    @Override
    public String getNameById(Long id) {
        if (null == id) return "";
        StoreshelfInfo storeshelfInfo = this.getById(id);
        return storeshelfInfo.getName();
    }


}
