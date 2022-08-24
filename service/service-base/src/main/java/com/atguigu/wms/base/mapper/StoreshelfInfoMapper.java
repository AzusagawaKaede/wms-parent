package com.atguigu.wms.base.mapper;

import com.atguigu.wms.model.base.StoreshelfInfo;
import com.atguigu.wms.vo.base.StoreareaInfoQueryVo;
import com.atguigu.wms.vo.base.StoreshelfInfoQueryVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface StoreshelfInfoMapper extends BaseMapper<StoreshelfInfo> {

    /**
     * 分页条件查询
     * @param page
     * @param storeshelfInfoQueryVo
     * @return
     */
    Page<StoreshelfInfo> findPage(Page<StoreshelfInfo> page, @Param("storeshelfInfoQueryVo") StoreshelfInfoQueryVo storeshelfInfoQueryVo);

    /**
     * 新增库位
     * @param storeareaId
     * @return
     */
    @Update("update storeshelf_info set storehouse_count = storehouse_count + 1 where id = #{storeareaId}")
    int addStoreStorehouse(Long storeareaId);

    /**
     * 减少库位
     * @param storeareaId
     * @return
     */
    @Update("update storeshelf_info set storehouse_count = storehouse_count - 1 where id = #{storeareaId}")
    int decreaseStorehouse(Long storeareaId);
}
