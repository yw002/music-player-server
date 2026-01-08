package com.example.musicplayer.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class Consumer implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 用户名称
     */
    private String username;

    /**
     * 用户密码
     */
    private String password;

    /**
     * 性别
     */
    private Integer sex;

    /**
     * 手机号码
     */
    private String phoneNum;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 生日
     */
    private String birth;

    /**
     * 自我介绍
     */
    private String introduction;

    /**
     * 地址
     */
    private String location;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 创建时间
     */
    private String createTime;

    /**
     * 更新时间
     */
    private String updateTime;
}
