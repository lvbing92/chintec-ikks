package com.chintec.ikks.auth.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chintec.ikks.auth.mapper.CredentialsAuthoritiesMapper;
import com.chintec.ikks.auth.service.ICredentialsAuthoritiesService;
import com.chintec.ikks.common.entity.CredentialsAuthorities;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author Jeff·Tang
 * @since 2020-08-26
 */
@Service
public class CredentialsAuthoritiesServiceImpl extends ServiceImpl<CredentialsAuthoritiesMapper, CredentialsAuthorities>
        implements ICredentialsAuthoritiesService {

}
