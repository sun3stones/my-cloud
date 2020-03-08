package com.lei.oauth.service.impl;

import com.lei.oauth.entity.OauthUser;
import com.lei.oauth.mapper.OauthUserMapper;
import com.lei.oauth.service.IOauthUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author sunlei
 * @since 2020-01-11
 */
@Service
public class OauthUserServiceImpl extends ServiceImpl<OauthUserMapper, OauthUser> implements IOauthUserService {

}
