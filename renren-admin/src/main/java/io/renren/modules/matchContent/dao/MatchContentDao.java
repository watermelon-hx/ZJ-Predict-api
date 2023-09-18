package io.renren.modules.matchContent.dao;

import io.renren.common.dao.BaseDao;
import io.renren.modules.matchContent.entity.MatchContentEntity;
import org.apache.ibatis.annotations.Mapper;

/**
* 比赛动态数据内容
*
* @author Mark sunlightcs@gmail.com
* @since 3.0 2023-07-14
*/
@Mapper
public interface MatchContentDao extends BaseDao<MatchContentEntity> {
	
}