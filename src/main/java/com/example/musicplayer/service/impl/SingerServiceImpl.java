package com.example.musicplayer.service.impl;

import com.example.musicplayer.entity.Singer;
import com.example.musicplayer.mapper.SingerMapper;
import com.example.musicplayer.service.ISingerService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class SingerServiceImpl extends ServiceImpl<SingerMapper, Singer> implements ISingerService {

}
