package com.example.musicplayer.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.musicplayer.entity.Comment;
import com.example.musicplayer.service.ICommentService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/comment")
public class CommentController {

    //业务层的对象
    @Autowired
    ICommentService commentService;

    //json的解析工具
    ObjectMapper jsonTool = new ObjectMapper();

    //根据歌单id和用户id查询歌单的评分    http://localhost:8085/music/comment/songList/detail
    @RequestMapping("/songList/detail")
    public String getsongListDetail(Integer songListId) throws JsonProcessingException {
        //map集合【键值对】
        HashMap result = new HashMap<>();
        //调用了业务层的方法查询
        QueryWrapper<Comment> wrapper = new QueryWrapper<>();
        //条件
        wrapper.eq("song_list_id", songListId);
        //查询
        List<Comment> list = commentService.list(wrapper);
        // key - value
        result.put("code", 1);
        result.put("list", list);
        //返回json格式的数据【字符串】
        return jsonTool.writeValueAsString(result);
    }

    //根据歌单评价提交   http://localhost:8085/music/comment/add
    @RequestMapping("/add")
    public String add(Comment comment) throws JsonProcessingException {
        //map集合【键值对】
        HashMap result = new HashMap<>();
        //获取当前时间
        String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        comment.setCreateTime(date);
        //提交数据
        boolean save = commentService.save(comment);
        // key - value
        result.put("code", save ? 1 : -1);
        //返回json格式的数据【字符串】
        return jsonTool.writeValueAsString(result);
    }
}