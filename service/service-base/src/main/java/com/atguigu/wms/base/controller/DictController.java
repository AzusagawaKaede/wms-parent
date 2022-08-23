package com.atguigu.wms.base.controller;

import com.alibaba.excel.EasyExcel;
import com.atguigu.wms.common.result.Result;
import com.atguigu.wms.base.service.DictService;
import com.atguigu.wms.model.base.Dict;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.util.List;

/**
 * @author qy
 */
//@CrossOrigin //跨域
@Api(tags = "数据字典管理")
@RestController
@RequestMapping(value = "/admin/base/dict")
public class DictController   {

    @Autowired
    private DictService dictService;

    @ApiOperation(value = "根据id获取名称")
    @GetMapping("getNameById/{id}")
    public String importData(@PathVariable Long id) {
        return dictService.getNameById(id);
    }

    /**
     * 根据父Id查询DictInfo列表
     * @param parentId
     * @return
     */
    @GetMapping("/findByParentId/{parentId}")
    public Result findByParentId(@PathVariable Long parentId){
        return Result.ok(dictService.findByParentId(parentId));
    }

    @GetMapping("/exportData")
    public void exportData(HttpServletResponse response) throws Exception{
        //查询所有数据
        List<Dict> data = dictService.list();
        //写入Excel
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setCharacterEncoding("utf-8");
        String fileName = URLEncoder.encode("数据字典", "UTF-8").replaceAll("\\+", "%20");
        response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");
        EasyExcel.write(response.getOutputStream(), Dict.class).sheet("数据字典").doWrite(data);
    }

//    @ApiOperation(value = "获取数据字典名称")
//    @GetMapping(value = "/getName/{parentDictCode}/{value}")
//    public String getName(
//            @ApiParam(name = "parentDictCode", value = "上级编码", required = true)
//            @PathVariable("parentDictCode") String parentDictCode,
//
//            @ApiParam(name = "value", value = "值", required = true)
//            @PathVariable("value") String value) {
//        return dictService.getNameByParentDictCodeAndValue(parentDictCode, value);
//    }

//    @ApiOperation(value = "获取数据字典名称")
//    @ApiImplicitParam(name = "value", value = "值", required = true, dataType = "Long", paramType = "path")
//    @GetMapping(value = "/getName/{value}")
//    public String getName(
//            @ApiParam(name = "value", value = "值", required = true)
//            @PathVariable("value") String value) {
//        return dictService.getNameByParentDictCodeAndValue("", value);
//    }
}

