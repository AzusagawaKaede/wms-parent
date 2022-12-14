package com.atguigu.wms.base.service;


import com.atguigu.wms.model.base.Dict;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;;import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface DictService extends IService<Dict> {

    String getNameById(Long id);

    /**
     * 根据父Id查询DictInfo列表
     * @param parentId
     * @return
     */
    List<Dict> findByParentId(Long parentId);

    /**
     * 根据编码对应的所有子节点
     * @param dictCode
     * @return
     */
    List<Dict> findByDictCode(String dictCode);

    /**
     * 根据上级编码与值获取数据字典名称
     * @param parentDictCode
     * @param value
     * @return
     */
    //String getNameByParentDictCodeAndValue(String parentDictCode, String value);

}
