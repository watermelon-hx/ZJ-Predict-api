package io.renren.modules.matchContent.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
* 比赛动态数据内容
*
* @author Mark sunlightcs@gmail.com
* @since 3.0 2023-07-14
*/
@Data
@ApiModel(value = "比赛动态数据内容")
public class MatchContentDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
    private Long id;
    @ApiModelProperty(value = "租户编码")
    private Long tenantCode;
    @ApiModelProperty(value = "创建者")
    private Long creator;
    @ApiModelProperty(value = "创建时间")
    private Date createDate;
    @ApiModelProperty(value = "更新者")
    private Long updater;
    @ApiModelProperty(value = "更新时间")
    private Date updateDate;
    private String contentInfo;
    private String contentRate;
    private Long matchScheduleId;

}