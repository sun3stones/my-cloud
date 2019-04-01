package com.lei.mywechat.entity;

import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author sunlei
 * @since 2019-04-01
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class WxUser implements Serializable {

    private static final long serialVersionUID = 1L;

    private String openid;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 微信昵称
     */
    private String wxname;

    /**
     * 微信头像
     */
    private String headimg;

    private LocalDateTime createTime;

    /**
     * 上次登录时间
     */
    private LocalDateTime loginTime;

    /**
     * 0为未认证，1为已认证
     */
    private Integer status;

    /**
     * 备注信息
     */
    private String remark;


}
