package com.atguigu.wms.base.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.atguigu.wms.model.base.Dict;
import com.atguigu.wms.model.base.GoodsType;
import com.atguigu.wms.model.base.StoreshelfInfo;
import com.atguigu.wms.vo.base.GoodsTypeQueryVo;
import com.atguigu.wms.base.mapper.GoodsTypeMapper;
import com.atguigu.wms.base.service.GoodsTypeService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

@Service
@SuppressWarnings({"unchecked", "rawtypes"})
public class GoodsTypeServiceImpl extends ServiceImpl<GoodsTypeMapper, GoodsType> implements GoodsTypeService {

	@Resource
	private GoodsTypeMapper goodsTypeMapper;

	/**
	 * 根据父Id查询子节点GoodsType
	 *
	 * @param parentId
	 * @return
	 */
	@Override
	public List<GoodsType> findByParentId(Long parentId) {
		if(parentId == null){
			throw new RuntimeException("参数错误");
		}
		return goodsTypeMapper.findByParentId(parentId);
	}

	/**
	 * 查询一二三三级分类
	 *
	 * @return
	 */
	@Override
	public List<GoodsType> findNodes() {
		//所有元素
		List<GoodsType> allGoodsTypes = goodsTypeMapper.findNotes();
		//定义一级节点集合
		List<GoodsType> result = new ArrayList<>();

		//找到所有的根节点
		allGoodsTypes.stream().forEach(l1goodType -> {
			//保存根节点
			if(l1goodType.getParentId() == 0){
				result.add(l1goodType);
			}

			if(l1goodType.isHasChildren()){
				//构建树
				List<GoodsType> children = new ArrayList<>();
				allGoodsTypes.stream().forEach(goodsType -> {
					if(goodsType.getParentId().equals(l1goodType.getId())){
						children.add(goodsType);
					}
				});
				l1goodType.setChildren(children);
			}
		});

		return result;
	}

	@Override
	public String getNameById(Long id) {
		GoodsType goodsType = this.getById(id);
		return goodsType.getName();
	}

}
