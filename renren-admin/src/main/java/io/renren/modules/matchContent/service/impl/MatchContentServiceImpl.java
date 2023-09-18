package io.renren.modules.matchContent.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.renren.common.context.TenantContext;
import io.renren.common.service.impl.CrudServiceImpl;
import io.renren.modules.matchContent.dao.MatchContentDao;
import io.renren.modules.matchContent.dto.MatchContentDTO;
import io.renren.modules.matchContent.entity.MatchContentEntity;
import io.renren.modules.matchContent.service.MatchContentService;
import io.renren.modules.security.user.SecurityUser;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 比赛动态数据内容
 *
 * @author Mark sunlightcs@gmail.com
 * @since 3.0 2023-07-14
 */
@Service
public class MatchContentServiceImpl extends CrudServiceImpl<MatchContentDao, MatchContentEntity, MatchContentDTO> implements MatchContentService {

    @Override
    public QueryWrapper<MatchContentEntity> getWrapper(Map<String, Object> params){
        QueryWrapper<MatchContentEntity> wrapper = new QueryWrapper<>();



        wrapper.eq("tenant_code", TenantContext.getTenantCode(SecurityUser.getUser()));








        return wrapper;
    }


}