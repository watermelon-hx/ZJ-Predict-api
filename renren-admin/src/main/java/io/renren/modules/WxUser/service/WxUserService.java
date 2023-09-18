package io.renren.modules.WxUser.service;

import io.renren.common.service.CrudService;
import io.renren.modules.WxUser.dto.WxUserDTO;
import io.renren.modules.WxUser.entity.WxUserEntity;
import java.util.List;

public interface WxUserService extends CrudService<WxUserEntity, WxUserDTO> {
    WxUserDTO getByOpenID(String paramString);

    List<WxUserDTO> getList(String paramString);
}
