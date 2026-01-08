package com.example.musicplayer.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.musicplayer.entity.MusicRank;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface MusicRankMapper extends BaseMapper<MusicRank> {
    @Select("select avg(score) from music_rank group by songListId having songListId=#{songListId};")
    public Integer getScore(@Param("songListId") Integer songListId);

}
