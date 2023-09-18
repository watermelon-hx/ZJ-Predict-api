package io.renren.modules.match.service;

import io.renren.common.page.PageData;
import io.renren.common.service.CrudService;
import io.renren.modules.match.dto.DuixianMatchDTO;
import io.renren.modules.match.dto.MatchScheduleDTO;
import io.renren.modules.match.dto.resultDto;
import io.renren.modules.match.entity.DuixianMatchEntity;
import io.renren.modules.match.entity.MatchScheduleEntity;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import java.util.Map;

/**
 * 日程
 *
 * @author sss 
 * @since 3.0 2023-03-04
 */
public interface MatchScheduleService extends CrudService<DuixianMatchEntity, DuixianMatchDTO> {
//    void getMatchList() throws IOException, ParseException;

//  PageData<MatchScheduleDTO> page(Map<String, Object> params);

    void realTimeStatistics() throws Exception;

    int insertMatchInfo(DuixianMatchDTO duixianMatchDTO);

//    MatchScheduleDTO getByMatchId(Integer matchId);

//    void checkMatchList(Integer time) throws Exception;

//    resultDto getRe(Date date);

  //  void getTimeRate(int id) throws IOException, ParseException;

//  void copyMatch() throws Exception;





}