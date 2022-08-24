package com.atguigu.wms.base.service.impl;

import com.atguigu.wms.base.service.StoreareaInfoService;
import com.atguigu.wms.base.service.StoreshelfInfoService;
import com.atguigu.wms.base.service.WarehouseInfoService;
import com.atguigu.wms.model.base.StoreareaInfo;
import com.atguigu.wms.model.base.StorehouseInfo;
import com.atguigu.wms.model.base.StoreshelfInfo;
import com.atguigu.wms.model.base.WarehouseInfo;
import com.atguigu.wms.vo.base.StorehouseInfoQueryVo;
import com.atguigu.wms.base.mapper.StorehouseInfoMapper;
import com.atguigu.wms.base.service.StorehouseInfoService;
import com.atguigu.wms.vo.base.StoreshelfInfoQueryVo;
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
public class StorehouseInfoServiceImpl extends ServiceImpl<StorehouseInfoMapper, StorehouseInfo> implements StorehouseInfoService {

	@Autowired
	private StorehouseInfoMapper storehouseInfoMapper;
	@Autowired
	private WarehouseInfoService warehouseInfoService;
	@Autowired
	private StoreareaInfoService storeareaInfoService;
	@Autowired
	private StoreshelfInfoService storeshelfInfoService;

	/**
	 * 分页条件连表查询
	 *
	 * @param page
	 * @param storehouseInfoQueryVo
	 * @return
	 */
	@Override
	public Page<StorehouseInfo> findPage(Page<StorehouseInfo> page, StorehouseInfoQueryVo storehouseInfoQueryVo) {
		if (storehouseInfoQueryVo == null) {
			storehouseInfoQueryVo = new StorehouseInfoQueryVo();
		}
		return storehouseInfoMapper.findPage(page, storehouseInfoQueryVo);
	}

	/**
	 * 货架
	 *
	 * @param storehouseInfo
	 * @return
	 */
	@Override
	public Boolean insert(StorehouseInfo storehouseInfo) {
		if (storehouseInfo == null) {
			throw new RuntimeException("参数错误");
		}
		//新增
		storehouseInfo.setCreateId(1L);
		storehouseInfo.setCreateName("admin");
		storehouseInfo.setUpdateId(1L);
		storehouseInfo.setUpdateName("admin");
		storehouseInfo.setCreateTime(new Date());
		storehouseInfo.setUpdateTime(new Date());
		storehouseInfo.setIsDeleted(0);
		int insert = storehouseInfoMapper.insert(storehouseInfo);
		if (insert <= 0) {
			throw new RuntimeException("新增失败");
		}
		//给仓库+1
		int update = warehouseInfoService.addStoreshelf(storehouseInfo.getWarehouseId());
		//给库区+1
		int update2 = storeareaInfoService.addStoreshlef(storehouseInfo.getStoreareaId());
		//给货架+1
		int update3 = storeshelfInfoService.addStoreStorehouse(storehouseInfo.getStoreshelfId());
		if (update <= 0 || update2 <= 0 || update3 <= 0) {
			throw new RuntimeException("新增失败");
		}
		return true;
	}

	/**
	 * 更新库区信息
	 *
	 * @param storehouseInfo
	 * @return
	 */
	@Override
	public Boolean updateStorehouseInfo(StorehouseInfo storehouseInfo) {
		//参数校验
		if (storehouseInfo == null || storehouseInfo.getId() == null) {
			throw new RuntimeException("参数错误");
		}
		//修改更新时间
		storehouseInfo.setUpdateId(1L);
		storehouseInfo.setUpdateName("admin");
		storehouseInfo.setUpdateTime(new Date());
		//查询原来的info
		StorehouseInfo info = storehouseInfoMapper.selectById(storehouseInfo.getId());
		//仓库-1
		int decrease = warehouseInfoService.decreaseStoreshelf(info.getWarehouseId());
		//库区-1
		int decrease2 = storeareaInfoService.decrease(info.getStoreareaId());
		//货架-1
		int decrease3 = storeshelfInfoService.decreaseStorehouse(info.getStoreshelfId());
		//新增
		int update = storehouseInfoMapper.updateById(storehouseInfo);
		//仓库+1
		int add = warehouseInfoService.addStoreshelf(storehouseInfo.getWarehouseId());
		//库区+1
		int add2 = storeareaInfoService.addStoreshlef(storehouseInfo.getStoreareaId());
		//货架+1
		int add3 = storeshelfInfoService.addStoreStorehouse(storehouseInfo.getStoreshelfId());
		if (decrease < 0 || decrease2 < 0 || decrease3 < 0 ||
				update < 0 || add < 0 || add2 < 0 || add3 < 0) {
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
	public Boolean removeStorehouseInfo(Long id) {
		//参数校验
		if (id == null) {
			throw new RuntimeException("参数错误");
		}
		//先查询货架数和库位数，非0不允许删除
		StorehouseInfo storehouseInfo = storehouseInfoMapper.selectById(id);
		//判断是否存在
		if (storehouseInfo == null) {
			return true;
		}
		//为0则删除
		int delete = storehouseInfoMapper.deleteById(id);
		//删除仓库的数量
		int delete2 = warehouseInfoService.decrease(storehouseInfo.getWarehouseId());
		//删除库区的数量
		int delete3 = storeareaInfoService.decrease(storehouseInfo.getStoreareaId());
		//删除货架的数量
		int delete4 = storeshelfInfoService.decreaseStorehouse(storehouseInfo.getStoreshelfId());
		if (delete <= 0 || delete2 <= 0 || delete3 <= 0 || delete4 <= 0) {
			throw new RuntimeException("删除失败");
		}
		return true;
	}

	@Override
	public List<StorehouseInfo> findByStoreshelfId(Long storeshelfId) {
		return this.list(new LambdaQueryWrapper<StorehouseInfo>().eq(StorehouseInfo::getStoreshelfId, storeshelfId));
	}

	@Cacheable(value = "storehouseInfo",keyGenerator = "keyGenerator")
	@Override
	public String getNameById(Long id) {
		if(null == id) {
			return "";
		}
		StorehouseInfo storehouseInfo = this.getById(id);
		return storehouseInfo.getName();
	}

	@Override
	public List<String> findNameByIdList(List<Long> idList) {
		List<String> list = new ArrayList<>();
		for(Long id : idList) {
			list.add(this.getNameById(id));
		}
		return list;
	}






}
