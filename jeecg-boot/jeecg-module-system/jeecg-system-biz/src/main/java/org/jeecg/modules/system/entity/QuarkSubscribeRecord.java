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
 * @Description: 夸克订阅记录
 * @Author: jeecg-boot
 * @Date: 2024-08-02
 * @Version: V1.0
 */
@Data
@TableName("quark_subscribe_record")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "quark_subscribe_record对象", description = "夸克订阅记录")
public class QuarkSubscribeRecord implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    @ApiModelProperty(value = "主键")
    private Long id;
    /**
     * 订阅名称
     */
    @Excel(name = "订阅名称", width = 15)
    @ApiModelProperty(value = "订阅名称")
    private String name;
    /**
     * 夸克账号ID
     */
    @Excel(name = "夸克账号ID", width = 15)
    @ApiModelProperty(value = "夸克账号ID")
    private Integer accountId;
    /**
     * 分享链接
     */
    @Excel(name = "分享链接", width = 15)
    @ApiModelProperty(value = "分享链接")
    private String shareUrl;
    /**
     * 保存的文件夹ID
     */
    @Excel(name = "保存的文件夹ID", width = 15)
    @ApiModelProperty(value = "保存的文件夹ID")
    private String toDirFid;

    private String sourceDirFid;

    private String prefixName;
    /**
     * 状态
     */
    @Excel(name = "状态", width = 15, dicCode = "subscribe_status")
    @Dict(dicCode = "subscribe_status")
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
