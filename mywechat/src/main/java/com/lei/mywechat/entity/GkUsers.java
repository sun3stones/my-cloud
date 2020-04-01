package com.lei.mywechat.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import java.time.LocalDate;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 用户表
 * </p>
 *
 * @author sunlei
 * @since 2019-12-19
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class GkUsers implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 用户名
     */
    private String userLogin;

    /**
     * 登录密码；sp_password加密
     */
    private String userPass;

    /**
     * 用户美名
     */
    private String userNicename;

    /**
     * 登录邮箱
     */
    private String userEmail;

    /**
     * 用户个人网站
     */
    private String userUrl;

    /**
     * 用户头像，相对于upload/avatar目录
     */
    private String avatar;

    /**
     * 性别；0：保密，1：男；2：女
     */
    private Integer sex;

    /**
     * 生日
     */
    private LocalDate birthday;

    /**
     * 个性签名
     */
    private String signature;

    /**
     * 最后登录ip
     */
    private String lastLoginIp;

    /**
     * 最后登录时间
     */
    private LocalDateTime lastLoginTime;

    /**
     * 注册时间
     */
    private LocalDateTime createTime;

    /**
     * 激活码
     */
    private String userActivationKey;

    /**
     * 用户状态 0：禁用； 1：正常 ；2：未验证
     */
    private Integer userStatus;

    /**
     * 用户积分
     */
    private Integer score;

    /**
     * 用户类型，1:admin ;2:会员
     */
    private Integer userType;

    /**
     * 金币
     */
    private Integer coin;

    /**
     * 手机号
     */
    private String mobile;


}
