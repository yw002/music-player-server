package com.example.musicplayer.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.musicplayer.entity.MusicRank;

public interface IMusicRankService extends IService<MusicRank> {
    public int getScore(Integer songListId);
}
