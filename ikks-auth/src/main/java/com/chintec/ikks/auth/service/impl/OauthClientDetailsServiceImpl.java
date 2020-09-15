package com.chintec.ikks.auth.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chintec.ikks.erp.entity.OauthClientDetails;
import com.chintec.ikks.erp.mapper.OauthClientDetailsMapper;
import com.chintec.ikks.erp.service.IOauthClientDetailsService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author ruBIn·lv
 * @since 2020-08-27
 */
@Service
public class OauthClientDetailsServiceImpl extends
        ServiceImpl<OauthClientDetailsMapper, OauthClientDetails>
        implements IOauthClientDetailsService {

}
