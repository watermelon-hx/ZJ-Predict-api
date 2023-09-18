package io.renren.modules.match.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.ContentRowHeight;
import com.alibaba.excel.annotation.write.style.HeadRowHeight;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * 日程
 *
 * @author sss 
 * @since 3.0 2023-03-04
 */
@Data
@ContentRowHeight(20)
@HeadRowHeight(20)
@ColumnWidth(25)
public class MatchScheduleExcel {


    /*-------------------比赛信息数据------------------------------------------------------------*/


    @ExcelProperty(value = "赛事名称", index = 1)
    private String competitionName;

//    @ExcelProperty(value = "主队名称", index = 2)
//    private String homeTeamName;
//
//    @ExcelProperty(value = "客队名称", index = 3)
//    private String awayTeamName;
//
//    @ExcelProperty(value = "比赛进行分钟数", index = 4)
//    private Integer matchNum;
//    @ExcelProperty(value = "比赛状态", index = 5)
//    private Integer statusId;
//    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
//    @ExcelProperty(value = "比赛时间", index = 6)
//    private Date matchTime;
//    @ExcelProperty(value = "初始盘口", index = 7)
//    private Double iniRate;
//    @ExcelProperty(value = "临场盘口", index = 8)
//    private Double startRate;
//    @ExcelProperty(value = "中场盘口", index = 9)
//    private Double midRate;
//    @ExcelProperty(value = "主队比分", index = 11)
//    private Integer homeScores;
//    @ExcelProperty(value = "客队比分", index = 12)
//    private Integer awayScores;
//    @ExcelProperty(value = "主队半场比分", index = 13)
//    private Integer homeHalfScores;
//    @ExcelProperty(value = "客队半场比分", index = 14)
//    private Integer awayHalfScores;
//    @ExcelProperty(value = "主队15分钟前比分", index = 15)
//    private Integer homeScores15;
//    @ExcelProperty(value = "客队15分钟前比分", index = 16)
//    private Integer awayScores15;
//    @ExcelProperty(value = "主队65分钟前比分", index = 17)
//    private Integer homeScores65;
//    @ExcelProperty(value = "客队65分钟前比分", index = 18)
//    private Integer awayScores65;
//    @ExcelProperty(value = "主队70分钟前比分", index = 19)
//    private Integer homeScores70;
//    @ExcelProperty(value = "客队70分钟前比分", index = 20)
//    private Integer awayScores70;
//
//    @ExcelProperty(value = "主队75分钟前比分", index = 21)
//    private Integer homeScores75;
//    @ExcelProperty(value = "客队75分钟前比分", index = 22)
//    private Integer awayScores75;
//
//    @ExcelProperty(value = "主队80分钟前比分", index = 23)
//    private Integer homeScores80;
//    @ExcelProperty(value = "客队80分钟前比分", index = 24)
//    private Integer awayScores80;
//
//    @ExcelProperty(value = "主队82分钟前比分", index = 25)
//    private Integer homeScores82;
//    @ExcelProperty(value = "客队82分钟前比分", index = 26)
//    private Integer awayScores82;
//
//    @ExcelProperty(value = "上半场角球", index = 27)
//    private Integer corner45;
//    @ExcelProperty(value = "65分角球", index = 28)
//    private Integer corner65;
//    @ExcelProperty(value = "主队75分钟前射正", index = 29)
//    private Integer homeShootStraight75;
//    @ExcelProperty(value = "客队75分钟射正", index = 30)
//    private Integer awayShootStraight75;
//
//    @ExcelProperty(value = "主队60分钟前射正", index = 31)
//    private Integer homeShootStraight60;
//    @ExcelProperty(value = "客队60分钟前射正", index = 32)
//    private Integer awayShootStraight60;
//    @ExcelProperty(value = "主队75分钟前射偏", index = 33)
//    private Integer homeShootBias75;
//    @ExcelProperty(value = "客队队75分钟前射偏", index = 34)
//    private Integer awayShootBias75;
//
//    @ExcelProperty(value = "主队60分钟前射偏", index = 35)
//    private Integer homeShootBias60;
//    @ExcelProperty(value = "客队60分钟前射偏", index = 36)
//    private Integer awayShootBias60;
//
//
////    @ExcelProperty(value = "82分钟x+0.5赔率")
////    private Double rate82;
//
//    @ExcelProperty(value = "半场满足规则", index = 37)
//    private String halfRules;
//
//
//    @ExcelProperty(value = "半场推荐是否成功", index = 38)
//    private String halfResults;
//
//
//
//
//
//    @ExcelProperty(value = "下半场满足规则", index = 39)
//    private String rules;
//
//
//
//
//    @ExcelProperty(value = "下半场推荐是否成功", index = 40)
//    private String results;
//
//    @ExcelProperty(value = "65分钟前比赛进攻总和", index = 41)
//    private int attack65;
//
//    @ExcelProperty(value = "65比赛危险进攻总和", index = 42)
//    private int dangerAttack65;
//
//
//    @ExcelProperty(value = "75分钟前比赛进攻总和", index = 43)
//    private int attack;
//
//    @ExcelProperty(value = "75分钟前比赛危险进攻总和", index = 44)
//    private int dangerAttack;
//
//
//    @ExcelProperty(value = "75分钟后是否有进球", index = 45)
//    private String isball;
//









}