package com.example.musicplayer.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class Singer implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 名称
     */
    private String name;

    /**
     * 性别
     */
    private Integer sex;

    /**
     * 图片
     */
    private String pic;

    /**
     * 生日
     */
    private String birth;

    /**
     * 地点
     */
    private String location;

    /**
     * 个人介绍
     */
    private String introduction;


}
