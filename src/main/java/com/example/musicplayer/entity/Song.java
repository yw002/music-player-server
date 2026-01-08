package com.example.musicplayer.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class Song implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 歌手id
     */
    private Integer singerId;

    /**
     * 歌手名称
     */
    @TableField(exist = false)
    private String singerName;

    /**
     * 名称
     */
    private String name;

    /**
     * 介绍
     */
    private String introduction;

    /**
     * 发行时间
     */
    private String createTime;

    /**
     * 修改时间
     */
    private String updateTime;

    /**
     * 图片
     */
    private String pic;

    /**
     * 歌词
     */
    private String lyric;

    /**
     * 访问地址
     */
    private String url;


}
