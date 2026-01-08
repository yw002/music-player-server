package com.example.musicplayer.service.impl;

import com.example.musicplayer.entity.Song;
import com.example.musicplayer.mapper.SongMapper;
import com.example.musicplayer.service.ISongService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class SongServiceImpl extends ServiceImpl<SongMapper, Song> implements ISongService {

}
