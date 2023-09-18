package io.renren.modules.match.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class DuixianMatchDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long id;

    private Integer duixianId;

    private String duixianKey;

    private String leagueId;

    private String league;

    private String teamHomeId;

    private String teamHomeName;

    private String teamAwayId;

    private String teamAwayName;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date beginDate;

    private String wholeScore;

    private String halfScore;

    private String score;

    private String sendTime;

    private int running;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date sendDate;

    private String rule;

    private String result;

}
