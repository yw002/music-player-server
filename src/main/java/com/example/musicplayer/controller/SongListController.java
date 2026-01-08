package com.example.musicplayer.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.musicplayer.entity.ListSong;
import com.example.musicplayer.entity.SongList;
import com.example.musicplayer.service.IListSongService;
import com.example.musicplayer.service.ISongListService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/songList")
public class SongListController {

    //提供业务层依赖对象
    @Autowired
    ISongListService songListService;

    @Autowired
    IListSongService listSongService;

    //json的解析工具
    ObjectMapper jsonTool = new ObjectMapper();

    // http://localhost:8085/music/songList/listALL
    @RequestMapping("/listALL")
    public String listALL() throws JsonProcessingException {
        //定义键值对集合map
        Map<String, Object> result = new HashMap<>();
        List<SongList> list = songListService.list();
        //返回list、total
        result.put("list", list);
        //返回json格式的数据
        return jsonTool.writeValueAsString(result);
    }

    // http://localhost:8085/music/songList/list?pageNum=2&size=4
    @RequestMapping("/list")
    public String list(@RequestParam(required = false, defaultValue = "") String name,
                       @RequestParam(required = false, defaultValue = "1") Integer pageNum,
                       @RequestParam(required = false, defaultValue = "10") Integer size) throws JsonProcessingException {
        //定义键值对集合map
        Map<String, Object> result = new HashMap<>();
        //构建分页查询对象
        IPage<SongList> page = new Page<>(pageNum, size);
        //构建条件构造器
        QueryWrapper<SongList> wrapper = new QueryWrapper<>();
        wrapper.like(name != null && !name.equals(""), "title", name);
        //将数据put到map中
        IPage<SongList> songListIPage = songListService.page(page, wrapper);
        List<SongList> list = songListIPage.getRecords();
        // 把歌单SongList中关联的歌曲id也查询出来
        list.forEach(songList -> {
            // 歌单id
            Integer songListId = songList.getId();
            // 关联表查询此歌单id关联的所有歌曲
            QueryWrapper<ListSong> getSongIdsWrapper = new QueryWrapper<>();
            getSongIdsWrapper.eq("song_list_id", songListId);
            List<ListSong> listSongs = listSongService.list(getSongIdsWrapper);
            // 歌单关联表中的所有歌曲id
            List<Integer> songListIds = new ArrayList<>();
            listSongs.forEach(listSong -> {
                songListIds.add(listSong.getSongId());
            });
            // 将关联id设置回分页的查询结果，以便前端渲染
            songList.setSongIds(songListIds);
        });
        //返回list、total
        result.put("list", list);
        result.put("total", songListIPage.getTotal());
        //返回json格式的数据
        return jsonTool.writeValueAsString(result);
    }

    //查询歌单的请求    http://localhost:8085/music/song-list/detail?songListId=${songListId}
    @RequestMapping("/detail")
    public String detail(Integer songListId) throws JsonProcessingException {
        //map集合【键值对】
        HashMap result = new HashMap<>();
        //条件
        QueryWrapper<ListSong> wrapper = new QueryWrapper<>();
        //根据歌单id查询
        wrapper.eq("song_list_id", songListId);
        //查询
        List<ListSong> list = listSongService.list(wrapper);
        // key - value
        result.put("list", list);
        //返回json格式的数据【字符串】
        return jsonTool.writeValueAsString(result);
    }

    //根据风格查询歌单的请求    http://localhost:8085/music/song-list/index_songList
    @RequestMapping("/style/detail")
    public String queryStyleDetail(String style) throws JsonProcessingException {
        //map集合【键值对】
        HashMap result = new HashMap<>();
        //条件构造
        QueryWrapper<SongList> wrapper = new QueryWrapper<>();
        wrapper.like("style", style);
        //查询wrapper
        List<SongList> list = songListService.list(wrapper);
        // key - value
        result.put("list", list);
        //返回json格式的数据【字符串】
        return jsonTool.writeValueAsString(result);
    }

    //歌单根据title进行歌单模糊查询的请求处理方法-http
    @RequestMapping(value = "/likeTitle/detail", method = RequestMethod.GET)
    public String likeTitleDetail(String title) throws JsonProcessingException {
        //hashmap
        HashMap result = new HashMap();
        //
        QueryWrapper<SongList> wrapper = new QueryWrapper<>();
        wrapper.like("title", title);
        // 条件查询-title进行歌单模糊查询
        List<SongList> list = songListService.list(wrapper);
        //存放集合数据
        result.put("list", list);
        //返回json格式
        return jsonTool.writeValueAsString(result);
    }


    // 新增/更新歌单
    @RequestMapping("/update")  // @RequestBody 表示解析json格式的数据【反序列化】
    public String update(@RequestBody SongList songList) throws JsonProcessingException {
        // map
        HashMap<String, Object> result = new HashMap<>();
        // 执行新增-修改 saveOrUpdate【判断id及记录是否存在，存在则进行修改操作，不存在则新增操作】
        boolean success = songListService.saveOrUpdate(songList);
        // 将歌单和歌曲的信息保存到关联表 list_song 多对多
        if (songList.getSongIds() != null) {
            // 首先删除这个歌单已经存在的关联关系
            QueryWrapper<ListSong> wrapper = new QueryWrapper<>();
            wrapper.eq("song_list_id", songList.getId());
            boolean remove = listSongService.remove(wrapper);
            if (remove) {
                System.out.println("删除了歌单原本关联的数据：" + remove);
            }
            // 新增歌单也会有歌单的ID
            Integer songListId = songList.getId();
            System.out.println(songListId);
            songList.getSongIds().forEach(id -> {
                ListSong listSong = new ListSong();
                listSong.setSongId(id);
                listSong.setSongListId(songListId);
                listSongService.save(listSong);
            });
        }
        // 将结果存放map中
        result.put("success", success);
        // 返回json格式的字符串
        return jsonTool.writeValueAsString(result);
    }

    // 删除歌单
    @RequestMapping("/delete")
    public String delete(Integer id) throws JsonProcessingException {
        //map集合【键值对】
        HashMap result = new HashMap<>();
        // 执行删除
        boolean delete = songListService.removeById(id);
        // 删除歌单的同时要删除歌单关联的歌曲
        QueryWrapper<ListSong> removeBySongId = new QueryWrapper<ListSong>().eq("song_list_id", id);
        boolean remove = listSongService.remove(removeBySongId);
        if (remove) {
            System.out.println("删除了歌单关联的歌曲" + remove);
        }
        // 将结果存map中
        result.put("success", delete);
        //返回json格式的数据【字符串】
        return jsonTool.writeValueAsString(result);
    }
}