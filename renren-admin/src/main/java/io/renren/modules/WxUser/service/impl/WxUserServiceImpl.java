package io.renren.modules.WxUser.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.renren.common.context.TenantContext;
import io.renren.common.service.impl.CrudServiceImpl;
import io.renren.modules.WxUser.dao.WxUserDao;
import io.renren.modules.WxUser.dto.WxUserDTO;
import io.renren.modules.WxUser.entity.WxUserEntity;
import io.renren.modules.WxUser.service.WxUserService;
import io.renren.modules.security.user.SecurityUser;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;

@Service
public class WxUserServiceImpl extends CrudServiceImpl<WxUserDao, WxUserEntity, WxUserDTO> implements WxUserService {
    public QueryWrapper<WxUserEntity> getWrapper(Map<String, Object> params) {
        QueryWrapper<WxUserEntity> wrapper = new QueryWrapper();
        wrapper.eq("tenant_code", TenantContext.getTenantCode(SecurityUser.getUser()));
        return wrapper;
    }

    public WxUserDTO getByOpenID(String openId) {
        WxUserDTO byOpenID = ((WxUserDao)this.baseDao).getByOpenID(openId);
        return byOpenID;
    }

    public List<WxUserDTO> getList(String date) {
        List<WxUserDTO> list = ((WxUserDao)this.baseDao).getList(date);
        return list;
    }
}
