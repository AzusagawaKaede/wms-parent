package com.atguigu.wms.model;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Data
public class BaseEntity implements Serializable {

    @ExcelProperty("ID")
    @ApiModelProperty(value = "id")
    @TableId(type = IdType.AUTO)
    private Long id;

    @ExcelProperty("创建人ID")
    @ApiModelProperty(value = "创建人")
    @TableField(value = "create_id", fill = FieldFill.INSERT)
    private Long createId;

    @ExcelProperty("创建人")
    @ApiModelProperty(value = "创建人")
    @TableField(value = "create_name", fill = FieldFill.INSERT)
    private String createName;

    @ExcelProperty("更新人ID")
    @ApiModelProperty(value = "更新人")
    @TableField(value = "update_id", fill=FieldFill.UPDATE)
    private Long updateId;

    @ExcelProperty("更新人")
    @ApiModelProperty(value = "更新人")
    @TableField(value = "update_name", fill=FieldFill.UPDATE)
    private String updateName;

    @ExcelProperty("创建时间")
    @ApiModelProperty(value = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField("create_time")
    private Date createTime;

    @ExcelProperty("更新时间")
    @ApiModelProperty(value = "更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField("update_time")
    private Date updateTime;

    @ExcelIgnore
    @ApiModelProperty(value = "逻辑删除(1:已删除，0:未删除)")
    @JsonIgnore
    @TableLogic
    @TableField("is_deleted")
    private Integer isDeleted;

    @ExcelIgnore
    @ApiModelProperty(value = "其他参数")
    @TableField(exist = false)
    private Map<String,Object> param = new HashMap<>();
}
