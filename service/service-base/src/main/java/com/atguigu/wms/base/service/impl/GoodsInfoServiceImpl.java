package com.atguigu.wms.base.service.impl;

import com.atguigu.wms.base.service.*;
import com.atguigu.wms.common.execption.WmsException;
import com.atguigu.wms.common.result.ResultCodeEnum;
import com.atguigu.wms.model.base.*;
import com.atguigu.wms.vo.base.GoodsInfoQueryVo;
import com.atguigu.wms.base.mapper.GoodsInfoMapper;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.*;

@Service
@Transactional(rollbackFor = Exception.class)
@SuppressWarnings({"unchecked", "rawtypes"})
public class GoodsInfoServiceImpl extends ServiceImpl<GoodsInfoMapper, GoodsInfo> implements GoodsInfoService {

    @Resource
    private GoodsInfoMapper goodsInfoMapper;
    @Resource
    private DictService dictService;
    @Resource
    private WarehouseInfoService warehouseInfoService;
    @Resource
    private GoodsTypeService goodsTypeService;
    @Resource
    private GoodsSkuRelationService goodsSkuRelationService;

    /**
     * 分页条件查询
     *
     * @param page
     * @param goodsInfoQueryVo
     * @return
     */
    @Override
    public Page<GoodsInfo> findPage(Page<GoodsInfo> page, GoodsInfoQueryVo goodsInfoQueryVo) {
        //分页连表查询
        return goodsInfoMapper.findPage(page, goodsInfoQueryVo);
    }

    /**
     * 新增货品
     *
     * @param goodsInfo
     * @return
     */
    @Override
    public Boolean insert(GoodsInfo goodsInfo) {
        //参数校验
        if (goodsInfo == null) {
            throw new RuntimeException("参数错误");
        }
        //判断skuId是否已经被关联过
        GoodsSkuRelation relation = goodsSkuRelationService.getBySkuId(goodsInfo.getSkuId());
        if (relation != null) {
            //说明被关联过
            return false;
        }
        //没有关联过则新增goods_info和goods_sku_relation
        goodsInfo.setCreateId(1L);
        goodsInfo.setCreateName("admin");
        goodsInfo.setUpdateId(1L);
        goodsInfo.setUpdateName("admin");
        goodsInfo.setCreateTime(new Date());
        goodsInfo.setUpdateTime(new Date());
        goodsInfo.setIsDeleted(0);
        int insert = goodsInfoMapper.insert(goodsInfo);
        if (insert <= 0) {
            throw new RuntimeException("新增失败");
        }
        //新增关系表
        relation = new GoodsSkuRelation();
        relation.setCreateId(1L);
        relation.setCreateName("admin");
        relation.setUpdateId(1L);
        relation.setUpdateName("admin");
        relation.setCreateTime(new Date());
        relation.setUpdateTime(new Date());
        relation.setIsDeleted(0);
        relation.setGoodsId(goodsInfo.getId());
        relation.setSkuId(goodsInfo.getSkuId());
        boolean save = goodsSkuRelationService.save(relation);
        if (!save) {
            throw new RuntimeException("绑定失败");
        }
        return true;
    }

    /**
     * 根据id查询货品
     *
     * @param id
     * @return
     */
    @Override
    public GoodsInfo get(Long id) {
        return goodsInfoMapper.get(id);
    }

    /**
     * 更新货品
     *
     * @param goodsInfo
     * @return
     */
    @Override
    public Boolean updateGoodsInfo(GoodsInfo goodsInfo) {
        //参数校验
        if (goodsInfo == null) {
            throw new RuntimeException("参数错误");
        }
        //更新goods_info
        goodsInfo.setUpdateId(1L);
        goodsInfo.setUpdateName("admin");
        goodsInfo.setUpdateTime(new Date());
        int update = goodsInfoMapper.updateById(goodsInfo);
        if (update < 0) {
            throw new RuntimeException("更新失败");
        }
        //更新goods_sku_relation表，一个skuId绑定一个goodsId，因此根据goodsId查询即可
        GoodsSkuRelation relation = goodsSkuRelationService.getByGoodsId(goodsInfo.getId());
        relation.setUpdateId(1L);
        relation.setUpdateName("admin");
        relation.setUpdateTime(new Date());
        relation.setSkuId(goodsInfo.getSkuId());
        boolean save = goodsSkuRelationService.updateById(relation);
        if (!save) {
            throw new RuntimeException("更新失败");
        }
        return true;
    }

    /**
     * 根据id删除GoodsInfo
     *
     * @param id
     * @return
     */
    @Override
    public Boolean removeGoodsInfo(Long id) {
        //参数校验
        if (id == null) {
            throw new RuntimeException("参数错误");
        }
        //删除goods_info
        int delete = goodsInfoMapper.deleteById(id);
        if (delete <= 0) {
            throw new RuntimeException("删除失败");
        }
        //删除goods_sku_relation
        boolean remove = goodsSkuRelationService.remove(
                new LambdaQueryWrapper<GoodsSkuRelation>()
                        .eq(GoodsSkuRelation::getGoodsId, id));
        if (!remove) {
            throw new RuntimeException("删除失败");
        }
        return true;
    }

    /**
     * 根据id和status修改启用和下线状态
     *
     * @param id
     * @param status
     * @return
     */
    @Override
    public Boolean updateStatus(Long id, Integer status) {
        return goodsInfoMapper.updateStatus(id, status);
    }

    private GoodsInfo packageGoodsInfo(GoodsInfo item) {
        item.setTemperatureTypeName(dictService.getNameById(item.getTemperatureTypeId()));
        item.setInspectTypeName(dictService.getNameById(item.getInspectTypeId()));
        item.setGoodsTypeName(goodsTypeService.getNameById(item.getGoodsTypeId()));
        item.setUnitName(dictService.getNameById(item.getUnitId()));
        item.setBaseUnitName(dictService.getNameById(item.getBaseUnitId()));
        return item;
    }

    @Override
    public List<GoodsInfo> findByKeyword(String keyword) {
        LambdaQueryWrapper<GoodsInfo> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.like(GoodsInfo::getName, keyword);
        List<GoodsInfo> list = this.list(queryWrapper);
        list.forEach(item -> {
            this.packageGoodsInfo(item);
        });
        return list;
    }

    @Override
    public GoodsInfo getGoodsInfo(Long id) {
        GoodsInfo goodsInfo = this.getById(id);
        GoodsSkuRelation goodsSkuRelation = goodsSkuRelationService.getByGoodsId(id);
        if (null != goodsSkuRelation) {
            goodsInfo.setSkuId(goodsSkuRelation.getSkuId());
        }
        return this.packageGoodsInfo(goodsInfo);
    }

    @Override
    public GoodsInfo getGoodsInfoBySkuId(Long skuId) {
        GoodsSkuRelation goodsSkuRelation = goodsSkuRelationService.getBySkuId(skuId);
        if (null == goodsSkuRelation) return null;
        GoodsInfo goodsInfo = this.getById(goodsSkuRelation.getGoodsId());
        if (null != goodsInfo) {
            goodsInfo.setSkuId(skuId);
        }
        return goodsInfo;
    }


    /**
     * 根据第三级分类id获取货品三级分类id列表
     *
     * @param goodsTypeId
     * @return
     */
    @Override
    public List<String> findGoodsTypeIdList(Long goodsTypeId) {
        GoodsType goodsType1 = goodsTypeService.getById(goodsTypeId);
        GoodsType goodsType2 = goodsTypeService.getById(goodsType1.getParentId());
        List<String> list = new ArrayList<>();
        list.add(goodsType2.getParentId().toString());
        list.add(goodsType1.getParentId().toString());
        list.add(goodsTypeId.toString());
        return list;
    }


}
