package com.example.musicplayer.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.musicplayer.entity.MusicRank;
import com.example.musicplayer.mapper.MusicRankMapper;
import com.example.musicplayer.service.IMusicRankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MusicRankServiceImpl extends ServiceImpl<MusicRankMapper, MusicRank> implements IMusicRankService {
    @Autowired
    MusicRankMapper musicRankMapper;

    @Override
    public int getScore(Integer songListId) {
        return musicRankMapper.getScore(songListId);
    }
}
