package com.example.musicplayer.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.musicplayer.entity.MusicRank;
import com.example.musicplayer.service.IMusicRankService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
@RequestMapping("/rank")
public class MusicRankController {

    @Autowired
    IMusicRankService musicRankService;

    //    定义json 的解析工具
    ObjectMapper jsonTool = new ObjectMapper();

    //查询歌单的获取评分信息   http://localhost:8085/music/rank/getById
    @RequestMapping("/getById")
    public String getById(Integer songListId) throws JsonProcessingException {
        //map集合【键值对】
        HashMap result = new HashMap<>();
        //调用了业务层的方法获取评分信息
        int score = musicRankService.getScore(songListId);
        // key - value
        result.put("score", score);
        //返回json格式的数据【字符串】
        return jsonTool.writeValueAsString(result);
    }

    //提交评分请求    http://localhost:8085/music/rank/add
    @RequestMapping("/add")
    public String add(MusicRank rank) throws JsonProcessingException {
        //map集合【键值对】
        HashMap result = new HashMap<>();
        //调用了业务层的page方法分页查询
        boolean save = musicRankService.save(rank);
        // key - value
        result.put("code", save ? 1 : -1);
        //返回json格式的数据【字符串】
        return jsonTool.writeValueAsString(result);
    }

    //根据歌单id和用户id查询歌单的评分    http://localhost:8085/music/rank/getByUserAndRand
    @RequestMapping("/getByUserAndRand")
    public String getByUserAndRand(MusicRank rank) throws JsonProcessingException {
        //map集合【键值对】
        HashMap result = new HashMap<>();
        //调用了业务层的page方法查询
        QueryWrapper<MusicRank> wrapper = new QueryWrapper<>();
        //条件
        wrapper.eq("songListId", rank.getSongListId());
        wrapper.eq("consumerId", rank.getConsumerId());
        //查询
        MusicRank one = musicRankService.getOne(wrapper);
        // key - value
        if (one != null) {
            result.put("code", 1);
            result.put("score", one.getScore());
        } else {
            result.put("code", -1);//没有评分记录
        }
        //返回json格式的数据【字符串】
        return jsonTool.writeValueAsString(result);
    }
}