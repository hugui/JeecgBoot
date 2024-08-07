package org.jeecg.modules.system.entity;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.jeecg.common.aspect.annotation.Dict;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @Description: 夸克同步记录
 * @Author: jeecg-boot
 * @Date: 2024-08-02
 * @Version: V1.0
 */
@Data
@TableName("quark_sync_record")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "quark_sync_record对象", description = "夸克同步记录")
public class QuarkSyncRecord implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(type = IdType.AUTO)
    @ApiModelProperty(value = "id")
    private String id;
    /**
     * 订阅ID
     */
    @Excel(name = "订阅ID", width = 15)
    @ApiModelProperty(value = "订阅ID")
    private Long subscribeId;

    @Excel(name = "源文件ID", width = 15)
    @ApiModelProperty(value = "源文件ID")
    private String sourceFid;

    /**
     * 文件ID
     */
    @Excel(name = "文件ID", width = 15)
    @ApiModelProperty(value = "文件ID")
    private String fid;
    /**
     * 文件名
     */
    @Excel(name = "原始文件名", width = 15)
    @ApiModelProperty(value = "原始文件名")
    private String fileName;

    @Excel(name = "文件名", width = 15)
    @ApiModelProperty(value = "文件名")
    private String name;
    /**
     * 文件类型
     */
    @Excel(name = "文件类型", width = 15)
    @ApiModelProperty(value = "文件类型")
    private Integer fileType;
    /**
     * 文件夹ID
     */
    @Excel(name = "文件夹ID", width = 15)
    @ApiModelProperty(value = "文件夹ID")
    private String dirFid;
    /**
     * 是否是文件夹
     */
    @Excel(name = "是否是文件夹", width = 15)
    @ApiModelProperty(value = "是否是文件夹")
    private Integer isDir;
    /**
     * 状态
     */
    @Excel(name = "状态", width = 15)
    @ApiModelProperty(value = "状态")
    private Integer status;
    /**
     * 创建人
     */
    @ApiModelProperty(value = "创建人")
    private String createBy;
    /**
     * 创建日期
     */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "创建日期")
    private Date createTime;
    /**
     * 更新人
     */
    @ApiModelProperty(value = "更新人")
    private String updateBy;
    /**
     * 更新日期
     */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "更新日期")
    private Date updateTime;
    /**
     * 是否删除
     */
    @Excel(name = "是否删除", width = 15)
    @ApiModelProperty(value = "是否删除")
    @TableLogic
    private Integer delFlag;
}
