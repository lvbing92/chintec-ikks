package com.chintec.ikks.auth.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.chintec.ikks.auth.entity.Credentials;
import com.chintec.ikks.auth.service.IAuthorityService;
import com.chintec.ikks.auth.service.ICredentialsAuthoritiesService;
import com.chintec.ikks.auth.service.ICredentialsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;


/**
 * @author rubin
 * @version 1.0
 * @date 2020/8/25 16:27
 */
@Service
public class UserDetailsServiceImpl implements
        UserDetailsService {

    @Autowired
    private ICredentialsService credentialsService;
    @Autowired
    private IAuthorityService iAuthorityService;
    @Autowired
    private ICredentialsAuthoritiesService iCredentialsAuthoritiesService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Credentials credentials = credentialsService.getOne(new QueryWrapper<Credentials>().lambda().eq(Credentials::getName, username));
        if (credentials == null) {
            throw new UsernameNotFoundException("User '" + username + "' can not be found");
        }
        /*Set<GrantedAuthority> collect = iCredentialsAuthoritiesService.list(new QueryWrapper<CredentialsAuthorities>().lambda()
                .eq(CredentialsAuthorities::getCredentialsId, credentials.getId()+1111111)).
                stream().map(credentialsAuthorities -> (GrantedAuthority) () -> iAuthorityService.getById(credentialsAuthorities.getAuthoritiesId()).getAuthority()).collect(Collectors.toSet());*/
        return new User(credentials.getName(), credentials.getPassword(), credentials.getEnabled(), true, true, true, new HashSet<GrantedAuthority>());
    }
}

