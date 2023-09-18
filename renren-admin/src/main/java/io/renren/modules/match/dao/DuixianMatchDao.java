package io.renren.modules.match.dao;

import io.renren.common.dao.BaseDao;
import io.renren.modules.match.dto.DuixianMatchDTO;
import io.renren.modules.match.dto.MatchScheduleDTO;
import io.renren.modules.match.dto.resultDto;
import io.renren.modules.match.entity.DuixianMatchEntity;
import io.renren.modules.match.entity.MatchScheduleEntity;
import liquibase.pro.packaged.S;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 日程
 *
 * @author sss
 * @since 3.0 2023-03-04
 */
@Mapper
public interface DuixianMatchDao extends BaseDao<DuixianMatchEntity> {

    DuixianMatchDTO getMatchByID(Long id);


    int insert(DuixianMatchDTO duixianMatchDTO);

    List<DuixianMatchDTO> getFinishedList();

    int updateStatusByKey(String key);
}