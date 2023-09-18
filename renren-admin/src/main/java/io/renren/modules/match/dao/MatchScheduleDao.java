package io.renren.modules.match.dao;

import io.renren.common.dao.BaseDao;
import io.renren.modules.match.dto.MatchScheduleDTO;
import io.renren.modules.match.dto.resultDto;
import io.renren.modules.match.entity.MatchScheduleEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 日程
 *
 * @author sss
 * @since 3.0 2023-03-04
 */
@Mapper
public interface MatchScheduleDao extends BaseDao<MatchScheduleEntity> {


    MatchScheduleDTO getByMatchId(Integer matchId);


    List<MatchScheduleDTO> getList(Map<String, Object> params);

    List<MatchScheduleDTO> getByMatchTime(Integer time);

    resultDto getReA(String date);

    resultDto getReA2(String date);

    resultDto getReG(String date);
    resultDto getReG2(String date);
    resultDto getReF(String date);

    resultDto getReX(String date);


    resultDto getReAHis();
    resultDto getReA2His();


    resultDto getReGHis();
    resultDto getReG2His();
    resultDto getReFHis();
    resultDto getReXHis();

    List<String> getCompetitionRate(String competitionName);

    List<String> getCompetitionBigRate();


    void copyInfo(String date);

    void deleInfo(String date);
}