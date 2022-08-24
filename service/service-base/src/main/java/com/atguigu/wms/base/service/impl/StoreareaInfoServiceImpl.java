package com.atguigu.wms.base.service.impl;

import com.atguigu.wms.base.mapper.WarehouseInfoMapper;
import com.atguigu.wms.base.service.DictService;
import com.atguigu.wms.base.service.WarehouseInfoService;
import com.atguigu.wms.common.result.Result;
import com.atguigu.wms.model.base.StoreareaInfo;
import com.atguigu.wms.model.base.StorehouseInfo;
import com.atguigu.wms.model.base.WarehouseInfo;
import com.atguigu.wms.vo.base.StoreareaInfoQueryVo;
import com.atguigu.wms.base.mapper.StoreareaInfoMapper;
import com.atguigu.wms.base.service.StoreareaInfoService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.*;

@Service
@SuppressWarnings({"unchecked", "rawtypes"})
public class StoreareaInfoServiceImpl extends ServiceImpl<StoreareaInfoMapper, StoreareaInfo> implements StoreareaInfoService {

    @Autowired
    private StoreareaInfoMapper storeareaInfoMapper;
    @Autowired
	private WarehouseInfoService warehouseInfoService;

    /**
     * 分页条件连表查询
     *
     * @param page
     * @param storeareaInfoQueryVo
     * @return
     */
    @Override
    public Page<StoreareaInfo> findPage(Page<StoreareaInfo> page, StoreareaInfoQueryVo storeareaInfoQueryVo) {
        if (storeareaInfoQueryVo == null) {
            storeareaInfoQueryVo = new StoreareaInfoQueryVo();
        }
        return storeareaInfoMapper.findPage(page, storeareaInfoQueryVo);
    }

    /**
     * 新增库区
     *
     * @param storeareaInfo
     * @return
     */
    @Override
    public Boolean insert(StoreareaInfo storeareaInfo) {
        if (storeareaInfo == null) {
            throw new RuntimeException("参数错误");
        }
        //新增
        storeareaInfo.setStorehouseCount(0);
        storeareaInfo.setCreateId(1L);
        storeareaInfo.setCreateName("admin");
        storeareaInfo.setUpdateId(1L);
        storeareaInfo.setUpdateName("admin");
        storeareaInfo.setCreateTime(new Date());
        storeareaInfo.setUpdateTime(new Date());
        storeareaInfo.setIsDeleted(0);
        int insert = storeareaInfoMapper.insert(storeareaInfo);
        if (insert <= 0) {
            throw new RuntimeException("新增失败");
        }
        //给仓库+1
        int update = warehouseInfoService.addStorearea(storeareaInfo.getWarehouseId());
        if (update <= 0) {
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
    public Boolean updateStoreareaInfo(StoreareaInfo storeareaInfo) {
        //参数校验
        if (storeareaInfo == null || storeareaInfo.getId() == null) {
            throw new RuntimeException("参数错误");
        }
        //修改更新时间
		storeareaInfo.setUpdateId(1L);
		storeareaInfo.setUpdateName("admin");
		storeareaInfo.setUpdateTime(new Date());
        //查询原来的info，判断仓库id是否一致
        StoreareaInfo info = storeareaInfoMapper.selectById(storeareaInfo.getId());
        if (info.getWarehouseId().equals(storeareaInfo.getWarehouseId())) {
            //一致直接写入
            int update = storeareaInfoMapper.updateById(storeareaInfo);
            if (update < 0) {
                throw new RuntimeException("更新失败");
            }
        } else {
            //不一致先对仓库-1，然后新增，再对新仓库+1
			int decrease = warehouseInfoService.decrease(info.getWarehouseId());
			int update = storeareaInfoMapper.updateById(storeareaInfo);
			int add = warehouseInfoService.addStorearea(storeareaInfo.getWarehouseId());
			if(decrease <=0 || update <= 0 || add <= 0){
				throw new RuntimeException("更新失败");
			}
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
	public Boolean removeStoreareaInfo(Long id) {
		//参数校验
		if (id == null) {
			throw new RuntimeException("参数错误");
		}
		//先查询货架数和库位数，非0不允许删除
		StoreareaInfo storeareaInfo = storeareaInfoMapper.selectById(id);
		//判断是否存在
		if (storeareaInfo != null &&
				(storeareaInfo.getStorehouseCount() != 0 ||
				storeareaInfo.getStoreshelfCount() != 0)) {
			throw new RuntimeException("货架数或库存数不为0，不可删除");
		}
		//为0则删除
		int delete = storeareaInfoMapper.deleteById(id);
		int delete2 = warehouseInfoService.decrease(storeareaInfo.getWarehouseId());
		if(delete < 0 || delete2 < 0){
			throw new RuntimeException("删除失败");
		}
		return true;
	}

	@Cacheable(value = "storeareaInfo", keyGenerator = "keyGenerator")
    @Override
    public String getNameById(Long id) {
        if (null == id) return "";
        StoreareaInfo storeareaInfo = this.getById(id);
        return storeareaInfo.getName();
    }


}
