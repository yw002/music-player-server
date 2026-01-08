package com.example.musicplayer.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
public class SongList implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 标题
     */
    private String title;

    /**
     * 图片
     */
    private String pic;

    /**
     * 介绍
     */
    private String introduction;

    /**
     * 风格
     */
    private String style;

    /**
     * 关联的歌曲ID
     */
    @TableField(exist = false)
    private List<Integer> songIds;
}
