package com.lei.cloud.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * 授权认证业务类
 * 
 * @author niugang
 * UserDetailsService spring security包里面的
 * 重写loadUserByUsername方法
 *
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {


	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return null;

	}

}
