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
 * @Description: 夸克账户
 * @Author: jeecg-boot
 * @Date: 2024-08-01
 * @Version: V1.0
 */
@Data
@TableName("quark_account")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "quark_account对象", description = "夸克账户")
public class QuarkAccount implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    @TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "编号")
    private Integer id;
    /**
     * 头像
     */
    @Excel(name = "头像", width = 15)
    @ApiModelProperty(value = "头像")
    private String avatar;
    /**
     * 真实姓名
     */
    @Excel(name = "真实姓名", width = 15)
    @ApiModelProperty(value = "真实姓名")
    private String nickname;
    /**
     * 状态(1-正常,2-冻结)
     */
    @Excel(name = "状态(1-正常,2-冻结)", width = 15)
    @ApiModelProperty(value = "状态(1-正常,2-冻结)")
    private Integer status;
    /**
     * 用户cookie
     */
    @Excel(name = "用户cookie", width = 15)
    @ApiModelProperty(value = "用户cookie")
    private String cookie;
    /**
     * 删除状态(0-正常,1-已删除)
     */
    @Excel(name = "删除状态(0-正常,1-已删除)", width = 15)
    @ApiModelProperty(value = "删除状态(0-正常,1-已删除)")
    @TableLogic
    private Integer delFlag;
    /**
     * 创建人登录名称
     */
    @ApiModelProperty(value = "创建人登录名称")
    private String createBy;
    /**
     * 创建日期
     */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "创建日期")
    private Date createTime;
    /**
     * 更新人登录名称
     */
    @ApiModelProperty(value = "更新人登录名称")
    private String updateBy;
    /**
     * 更新日期
     */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "更新日期")
    private Date updateTime;
}
