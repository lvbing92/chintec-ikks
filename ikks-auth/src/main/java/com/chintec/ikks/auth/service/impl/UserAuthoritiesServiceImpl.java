package com.chintec.ikks.auth.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chintec.ikks.auth.entity.UserAuthorities;
import com.chintec.ikks.auth.mapper.UserAuthoritiesMapper;
import com.chintec.ikks.auth.service.IUserAuthoritiesService;
import org.springframework.stereotype.Service;

import java.io.Serializable;

/**
 * <p>
 * 公司人员角色关系表 服务实现类
 * </p>
 *
 * @author rubIn·lv
 * @since 2020-10-16
 */
@Service
public class UserAuthoritiesServiceImpl extends ServiceImpl<UserAuthoritiesMapper, UserAuthorities> implements IUserAuthoritiesService , Serializable {

}
