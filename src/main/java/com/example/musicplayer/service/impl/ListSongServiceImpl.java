package com.example.musicplayer.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.musicplayer.entity.ListSong;
import com.example.musicplayer.mapper.ListSongMapper;
import com.example.musicplayer.service.IListSongService;
import org.springframework.stereotype.Service;

@Service
public class ListSongServiceImpl extends ServiceImpl<ListSongMapper, ListSong> implements IListSongService {

}
