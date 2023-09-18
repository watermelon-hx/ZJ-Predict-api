package io.renren.modules.match.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 日程
 *
 * @author sss
 * @since 3.0 2023-03-04
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("match_schedule")
public class MatchScheduleEntity {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId
    private Long id;
    private Long matchId;
    /**
     * 租户编码
     */
    @TableField(fill = FieldFill.INSERT)
    private Long tenantCode;
    /**
     * 创建者
     */
    @TableField(fill = FieldFill.INSERT)
    private Long creator;
    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createDate;
    private Integer checkScores;

    /**
     * 更新者
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Long updater;
    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateDate;

    //   @ApiModelProperty(value = "赛事ID")
    private Integer competitionId;
    //   @ApiModelProperty(value = "赛事名称")
    private String competitionName;
    //   @ApiModelProperty(value = "主队ID")
    private Integer homeTeamId;
    //   @ApiModelProperty(value = "主队名称")
    private String homeTeamName;
    //   @ApiModelProperty(value = "客队ID")
    private Integer awayTeamId;
    //  @ApiModelProperty(value = "客队名称")
    private String awayTeamName;
    //   @ApiModelProperty(value = "比赛状态")
    private Integer statusId;
    //   @ApiModelProperty(value = "比赛时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date matchTime;
    //  @ApiModelProperty(value = "初始盘口")
    private Double iniRate;
    //  @ApiModelProperty(value = "临场盘口")
    private Double startRate;
    //  @ApiModelProperty(value = "中场盘口")
    private Double midRate;
    //  @ApiModelProperty(value = "主队比分")
    private Integer homeScores;
    //  @ApiModelProperty(value = "客队比分")
    private Integer awayScores;
    //   @ApiModelProperty(value = "主队半场比分")
    private Integer homeHalfScores;
    //  @ApiModelProperty(value = "客队半场比分")
    private Integer awayHalfScores;

    //  @ApiModelProperty(value = "主队15分钟前比分")
    private Integer homeScores15;
    //   @ApiModelProperty(value = "客队15分钟前比分")
    private Integer awayScores15;


    //  @ApiModelProperty(value = "主队65分钟前比分")
    private Integer homeScores65;
    //  @ApiModelProperty(value = "客队65分钟前比分")
    private Integer awayScores65;

    //   @ApiModelProperty(value = "主队70分钟前比分")
    private Integer homeScores70;
    //  @ApiModelProperty(value = "客队70分钟前比分")
    private Integer awayScores70;


    //  @ApiModelProperty(value = "主队75分钟前比分")
    private Integer homeScores75;
    //  @ApiModelProperty(value = "客队75分钟前比分")
    private Integer awayScores75;

    //  @ApiModelProperty(value = "主队80分钟前比分")
    private Integer homeScores80;
    // @ApiModelProperty(value = "客队80分钟前比分")
    private Integer awayScores80;

    //  @ApiModelProperty(value = "主队82分钟前比分")
    private Integer homeScores82;
    //   @ApiModelProperty(value = "客队82分钟前比分")
    private Integer awayScores82;

    //  @ApiModelProperty(value = "上半场角球")
    private Integer corner45;
    //   @ApiModelProperty(value = "60分角球")
    private Integer corner65;
    //  @ApiModelProperty(value = "主队75分钟前射正")
    private Integer homeShootStraight75;
    // @ApiModelProperty(value = "客队75分钟射正")
    private Integer awayShootStraight75;

    // @ApiModelProperty(value = "主队60分钟前射正")
    private Integer homeShootStraight60;
    // @ApiModelProperty(value = "客队60分钟前射正")
    private Integer awayShootStraight60;


    //  @ApiModelProperty(value = "主队75分钟前射偏")
    private Integer homeShootBias75;
    //   @ApiModelProperty(value = "客队队75分钟前射偏")
    private Integer awayShootBias75;

    //  @ApiModelProperty(value = "主队60分钟前射偏")
    private Integer homeShootBias60;
    // @ApiModelProperty(value = "客队60分钟前射偏")
    private Integer awayShootBias60;


    // @ApiModelProperty(value = "82分钟x+0.5赔率")
    private Double rate82;


    // @ApiModelProperty(value = "下半场推荐满足规则")
    private String rules;

    //  @ApiModelProperty(value = "比赛进行分钟数")
    private Integer matchNum;

    // @ApiModelProperty(value = "推荐大小")
    private Double recommendedRate;


    /// @ApiModelProperty(value = "半场推荐")
    private String halfRules;

    private Integer homeShootStraight70;
    private Integer awayShootStraight70;

    private Integer homeShootBias70;
    private Integer awayShootBias70;

    private int attack70;

    private int dangerAttack70;
    private int homeDangerAttack60;
    private int awayDangerAttack60;
    private int homeAttack60;
    private int awayAttack60;


    private int homeDangerAttack70;
    private int awayDangerAttack70;
    private int homeAttack70;
    private int awayAttack70;

    private Integer homeCorner60;
    private Integer awayCorner60;
    private Integer homeCorner70;
    private Integer awayCorner70;

    // @ApiModelProperty(value = "半场推荐是否成功")
    private String halfResults;

    //  @ApiModelProperty(value = "全程推荐是否成功")
    private String results;


    private Double rateFor;

    private Double rateFor2;

    private String contentInfo;

    private String contentRate;




    private int attack;

    private int dangerAttack;


    private int attack65;

    private int dangerAttack65;


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