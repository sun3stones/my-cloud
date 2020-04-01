package com.lei.mywechat.mapper;

import com.lei.mywechat.entity.GkUsers;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lei.mywechat.utils.datasource.DS;

/**
 * <p>
 * 用户表 Mapper 接口
 * </p>
 *
 * @author sunlei
 * @since 2019-12-19
 */
@DS("dataSource2")
public interface GkUsersMapper extends BaseMapper<GkUsers> {

}
