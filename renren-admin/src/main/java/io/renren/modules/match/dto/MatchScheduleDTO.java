package io.renren.modules.match.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 日程
 *
 * @author sss
 * @since 3.0 2023-03-04
 */
@Data
@ApiModel(value = "日程")
public class MatchScheduleDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
    private Long id;
    private Long matchId;
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


    /*-------------------比赛信息数据------------------------------------------------------------*/

    @ApiModelProperty(value = "赛事ID")
    private Integer competitionId;
    @ApiModelProperty(value = "赛事名称")
    private String competitionName;
    @ApiModelProperty(value = "主队ID")
    private Integer homeTeamId;
    @ApiModelProperty(value = "主队名称")
    private String homeTeamName;
    @ApiModelProperty(value = "客队ID")
    private Integer awayTeamId;
    @ApiModelProperty(value = "客队名称")
    private String awayTeamName;
    @ApiModelProperty(value = "比赛状态")
    private Integer statusId;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty(value = "比赛时间")
    private Date matchTime;
    @ApiModelProperty(value = "初始盘口")
    private Double iniRate;
    @ApiModelProperty(value = "临场盘口")
    private Double startRate;
    @ApiModelProperty(value = "中场盘口")
    private Double midRate;
    @ApiModelProperty(value = "主队比分")
    private Integer homeScores;
    @ApiModelProperty(value = "客队比分")
    private Integer awayScores;
    @ApiModelProperty(value = "主队半场比分")
    private Integer homeHalfScores;
    @ApiModelProperty(value = "客队半场比分")
    private Integer awayHalfScores;

    @ApiModelProperty(value = "主队15分钟前比分")
    private Integer homeScores15;
    @ApiModelProperty(value = "客队15分钟前比分")
    private Integer awayScores15;


    @ApiModelProperty(value = "主队65分钟前比分")
    private Integer homeScores65;
    @ApiModelProperty(value = "客队65分钟前比分")
    private Integer awayScores65;

    @ApiModelProperty(value = "主队70分钟前比分")
    private Integer homeScores70;
    @ApiModelProperty(value = "客队70分钟前比分")
    private Integer awayScores70;

    @ApiModelProperty(value = "主队75分钟前比分")
    private Integer homeScores75;
    @ApiModelProperty(value = "客队75分钟前比分")
    private Integer awayScores75;

    @ApiModelProperty(value = "主队80分钟前比分")
    private Integer homeScores80;
    @ApiModelProperty(value = "客队80分钟前比分")
    private Integer awayScores80;

    @ApiModelProperty(value = "主队82分钟前比分")
    private Integer homeScores82;
    @ApiModelProperty(value = "客队82分钟前比分")
    private Integer awayScores82;

    @ApiModelProperty(value = "上半场角球")
    private Integer corner45;

    @ApiModelProperty(value = "60分角球")
    private Integer homeCorner60;
    @ApiModelProperty(value = "60分角球")
    private Integer awayCorner60;
    @ApiModelProperty(value = "70分角球")
    private Integer homeCorner70;
    @ApiModelProperty(value = "70分角球")
    private Integer awayCorner70;

    @ApiModelProperty(value = "65分角球")
    private Integer corner65;
    @ApiModelProperty(value = "主队75分钟前射正")
    private Integer homeShootStraight75;
    @ApiModelProperty(value = "客队75分钟射正")
    private Integer awayShootStraight75;

    @ApiModelProperty(value = "主队60分钟前射正")
    private Integer homeShootStraight60;
    @ApiModelProperty(value = "客队60分钟前射正")
    private Integer awayShootStraight60;


    @ApiModelProperty(value = "主队70分钟前射正")
    private Integer homeShootStraight70;
    @ApiModelProperty(value = "客队70分钟前射正")
    private Integer awayShootStraight70;

    @ApiModelProperty(value = "主队70分钟前射偏")
    private Integer homeShootBias70;
    @ApiModelProperty(value = "客队70分钟前射偏")
    private Integer awayShootBias70;

    @ApiModelProperty(value = "70比赛进攻总和")
    private int attack70;

    @ApiModelProperty(value = "70比赛危险进攻总和")
    private int dangerAttack70;


    @ApiModelProperty(value = "主队75分钟前射偏")
    private Integer homeShootBias75;
    @ApiModelProperty(value = "客队队75分钟前射偏")
    private Integer awayShootBias75;

    @ApiModelProperty(value = "主队60分钟前射偏")
    private Integer homeShootBias60;
    @ApiModelProperty(value = "客队60分钟前射偏")
    private Integer awayShootBias60;


    @ApiModelProperty(value = "82分钟x+0.5赔率")
    private Double rate82;


    @ApiModelProperty(value = "下半场推荐满足规则")
    private String rules;

    @ApiModelProperty(value = "比赛进行分钟数")
    private Integer matchNum;

    @ApiModelProperty(value = "推荐大小")
    private Double recommendedRate;


    @ApiModelProperty(value = "半场推荐")
    private String halfRules;


    @ApiModelProperty(value = "半场推荐是否成功")
    private String halfResults;

    @ApiModelProperty(value = "全程推荐是否成功")
    private String results;

    @ApiModelProperty(value = "比赛进攻总和")
    private int attack65;


    @ApiModelProperty(value = "比赛危险进攻总和")
    private int dangerAttack65;

    private int homeDangerAttack60;
    private int awayDangerAttack60;
    private int homeAttack60;
    private int awayAttack60;


    private int homeDangerAttack70;
    private int awayDangerAttack70;
    private int homeAttack70;
    private int awayAttack70;


    @ApiModelProperty(value = "比赛进攻总和")
    private int attack;

    @ApiModelProperty(value = "比赛危险进攻总和")
    private int dangerAttack;


    @ApiModelProperty(value = "特殊数据")
    private Double rateFor;

    @ApiModelProperty(value = "特殊数据")
    private Double rateFor2;


    @ApiModelProperty(value = "实时统计原生数据")
    private String contentInfo;

    @ApiModelProperty(value = "实时赔率原生数据")
    private String contentRate;

    @ApiModelProperty(value = "75分钟后是否有进球")
    private String isball;

    private String remark;


    //初盘
    private Double startLetPan;

    //70盘
    private Double letPan70;

    //80盘
    private Double letPan80;


    private Double bigRate70;
    private Double big70;
    private Double smallRate70;

    private Double bigRate80;
    private Double big80;
    private Double smallRate80;




}