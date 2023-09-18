package io.renren.modules.WxUser.dto;


import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
@Data
@ApiModel("WxUser")
public class WxUserDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @TableId
    private Long id;

    @TableField(fill = FieldFill.INSERT)
    private Long tenantCode;

    @TableField(fill = FieldFill.INSERT)
    private Long creator;

    @TableField(fill = FieldFill.INSERT)
    private Date createDate;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Long updater;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateDate;
    private String userName;
    private String openId;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date endDate;

    private String wxCode;
}
