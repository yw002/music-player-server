package com.example.musicplayer.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = false)
public class MusicRank implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 歌单编号
     */
    @TableField("songListId")
    private Long songListId;

    /**
     * 用户
     */
    @TableField("consumerId")
    private Long consumerId;

    /**
     * 评分
     */
    private Integer score;


}
