package io.renren.modules.WxUser.dao;

import io.renren.common.dao.BaseDao;
import io.renren.modules.WxUser.dto.WxUserDTO;
import io.renren.modules.WxUser.entity.WxUserEntity;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface WxUserDao extends BaseDao<WxUserEntity> {
    WxUserDTO getByOpenID(String paramString);

    List<WxUserDTO> getList(String paramString);
}
