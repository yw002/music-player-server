package com.example.musicplayer.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = false)
public class Comment implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 用户id
     */
    private Integer userId;

    /**
     * 歌曲id
     */
    private Integer songId;

    /**
     * 歌单id
     */
    private Integer songListId;

    /**
     * 内容
     */
    private String content;

    /**
     * 创建时间
     */
    private String createTime;

    /**
     * 类型
     */
    private Integer type;

    private Integer up;


}
