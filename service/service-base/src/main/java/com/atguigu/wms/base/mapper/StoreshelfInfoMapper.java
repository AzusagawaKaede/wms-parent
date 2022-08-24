package com.atguigu.wms.base.mapper;

import com.atguigu.wms.model.base.StoreshelfInfo;
import com.atguigu.wms.vo.base.StoreareaInfoQueryVo;
import com.atguigu.wms.vo.base.StoreshelfInfoQueryVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface StoreshelfInfoMapper extends BaseMapper<StoreshelfInfo> {

    /**
     * 分页条件查询
     * @param page
     * @param storeareaInfoQueryVo
     * @return
     */
    Page<StoreshelfInfo> findPage(Page<StoreshelfInfo> page, @Param("storeshelfInfoQueryVo") StoreshelfInfoQueryVo storeshelfInfoQueryVo);
}
