package com.example.musicplayer.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.musicplayer.entity.SongList;
import com.example.musicplayer.mapper.SongListMapper;
import com.example.musicplayer.service.ISongListService;
import org.springframework.stereotype.Service;

@Service
public class SongListServiceImpl extends ServiceImpl<SongListMapper, SongList> implements ISongListService {

}
