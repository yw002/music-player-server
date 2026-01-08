package com.example.musicplayer.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.musicplayer.entity.Collect;
import com.example.musicplayer.mapper.CollectMapper;
import com.example.musicplayer.service.ICollectService;
import org.springframework.stereotype.Service;

@Service
public class CollectServiceImpl extends ServiceImpl<CollectMapper, Collect> implements ICollectService {

}
