package com.example.musicplayer.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.musicplayer.entity.Collect;
import com.example.musicplayer.service.ICollectService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/collection")
public class CollectController {

    //业务层的对象
    @Autowired
    ICollectService collectService;

    //json的解析工具
    ObjectMapper jsonTool = new ObjectMapper();

    //1、根据用户id查询歌曲id数组  http://localhost:8085/music/collection/detail?userId=2
    @RequestMapping("/detail")
    public String singerDetail(Integer userId) throws JsonProcessingException {
        //map集合【键值对】
        HashMap result = new HashMap<>();
        //
        QueryWrapper<Collect> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", userId);
        //调用了业务层的查询
        List<Collect> list = collectService.list(wrapper);
        // key - value
        result.put("list", list);
        //返回json格式的数据【字符串】
        return jsonTool.writeValueAsString(result);
    }

    //收藏歌曲的请求处理方法-http
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String add(Collect collect) throws JsonProcessingException {
        //hashmap
        HashMap result = new HashMap();
        //判断数据的存在
        if (collect.getSongId() == null || collect.getSongId() == 0) {
            result.put("code", 0);//表示失败
            result.put("msg", "收藏歌曲为空！！");
            return jsonTool.writeValueAsString(result);
        }
        //判断已经收藏
        QueryWrapper<Collect> wrapper = new QueryWrapper<Collect>()
                .eq("user_id", collect.getUserId())
                .eq("song_id", collect.getSongId());
        Collect getOne = collectService.getOne(wrapper);
        if (getOne != null) {
            result.put("code", 2);//表示失败
            result.put("msg", "该歌曲已经收藏过了！！");
            return jsonTool.writeValueAsString(result);
        }
        //时间获取
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = dateFormat.format(new Date());
        collect.setCreateTime(time);
        //调用业务层来存储数据
        boolean save = collectService.save(collect);
        //返回结果[判断]
        if (save) {//成功操作
            result.put("code", 1);//表示成功
            result.put("msg", "收藏成功");
        } else {
            result.put("code", 0);//表示失败
            result.put("msg", "收藏错误");
        }
        //返回json格式
        return jsonTool.writeValueAsString(result);
    }
}