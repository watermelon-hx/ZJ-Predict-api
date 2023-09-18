package io.renren.modules.match.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
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
@TableName("duixian_match")
public class DuixianMatchEntity {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId
    private Long id;

    private Integer duixianId;

    private String duixianKey;


    private String leagueId;

    private String league;

    private String teamHomeId;

    private String teamHomeName;

    private String teamAwayId;

    private String teamAwayName;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date beginDate;

    private String wholeScore;

    private String halfScore;

    private String score;

    private String sendTime;

    private Integer running;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date sendDate;

    private String rule;
    private String result;

}