package com.example.musicplayer.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.musicplayer.entity.Singer;
import com.example.musicplayer.entity.Song;
import com.example.musicplayer.service.ISingerService;
import com.example.musicplayer.service.ISongService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/song")
public class SongController {


    //业务层的对象
    @Autowired
    ISongService songService;
    @Autowired
    ISingerService singerService;

    //json的解析工具
    ObjectMapper jsonTool = new ObjectMapper();

    // 获取所有歌曲列表，用户管理端关联歌曲
    @RequestMapping("/listALL")
    public String listALL() throws JsonProcessingException {
        //定义键值对集合map
        Map<String, Object> result = new HashMap<>();
        //将数据put到map中
        List<Song> list = songService.list();
        //返回list、total
        result.put("list", list);
        //返回json格式的数据
        return jsonTool.writeValueAsString(result);
    }

    //请求处理方法【http请求接口】  http://localhost:8080/consumer/list
    @RequestMapping("/list")
    public String list(
            @RequestParam(required = false, defaultValue = "") String name,
            @RequestParam(required = false, defaultValue = "1") Integer pageNum,
            @RequestParam(required = false, defaultValue = "4") Integer size
    ) throws JsonProcessingException {
        // map
        HashMap<String, Object> result = new HashMap<>();
        // 分页对象 第一个参数是页码，第二个参数是每页显示的记录值
        IPage<Song> page = new Page<>(pageNum, size);
        // 构建条件构造器
        QueryWrapper<Song> wrapper = new QueryWrapper<>();
        // 注入条件
        wrapper.like(name != null && !name.equals(""), "name", name);
        // page方法，注入wrapper
        IPage<Song> consumerIPage = songService.page(page, wrapper);
        // 获取结果list
        List<Song> list = consumerIPage.getRecords();
        // 关联查询歌手的name
        list.forEach(song -> {
            Singer singer = singerService.getById(song.getSingerId());
            if (singer != null) {
                song.setSingerName(singer.getName());
            }
        });
        // 将结果存放map中
        result.put("list", list);
        result.put("total", consumerIPage.getTotal());
        // 返回json格式的字符串
        return jsonTool.writeValueAsString(result);
    }

    //1、根据歌曲id查询歌曲  http://localhost:8085/music/song/detail?id=2
    @RequestMapping("/detail")
    public String detail(Integer id) throws JsonProcessingException {
        //map集合【键值对】
        HashMap result = new HashMap<>();
        //调用了业务层的查询
        Song song = songService.getById(id);
        // key - value
        result.put("song", song);
        //返回json格式的数据【字符串】
        return jsonTool.writeValueAsString(result);
    }

    //1、根据歌手id查询歌曲  http://localhost:8085/music/song/singer/detail?singerId=2
    @RequestMapping("/singer/detail")
    public String singerDetail(Integer singerId) throws JsonProcessingException {
        //map集合【键值对】
        HashMap result = new HashMap<>();
        //
        QueryWrapper<Song> wrapper = new QueryWrapper<>();
        wrapper.eq("singer_id", singerId);
        //调用了业务层的查询
        List<Song> list = songService.list(wrapper);
        // key - value
        result.put("list", list);
        //返回json格式的数据【字符串】
        return jsonTool.writeValueAsString(result);
    }

    //根据歌曲名查询歌曲的请求处理方法-http
    @RequestMapping(value = "/singerName/detail", method = RequestMethod.GET)
    public String singerNameDetail(String name) throws JsonProcessingException {
        //hashmap
        HashMap result = new HashMap();
        //查询条件like[模糊查询]
        QueryWrapper<Song> wrapper = new QueryWrapper<>();
        wrapper.like("name", name);
        //返回中间表的数据集合
        List<Song> list = songService.list(wrapper);
        //存放集合数据
        result.put("list", list);
        //返回json格式
        return jsonTool.writeValueAsString(result);
    }

    @RequestMapping("/update")  // @RequestBody 表示解析json格式的数据【反序列化】
    public String update(@RequestBody Song song) throws JsonProcessingException {
        // map
        HashMap<String, Object> result = new HashMap<>();
        // 表单验证
        if (song.getName() == null || song.getName().equals("")) {
            result.put("code", -1);//表示失败
            result.put("msg", "歌曲名格式，不能为空！！");
            return jsonTool.writeValueAsString(result);
        }
        if (song.getSingerId() == null ) {
            result.put("code", -1);//表示失败
            result.put("msg", "歌手选择，不能为空！！");
            return jsonTool.writeValueAsString(result);
        }
        // 执行新增-修改 saveOrUpdate【判断id及记录是否存在，存在则进行修改操作，不存在则新增操作】
        boolean success = songService.saveOrUpdate(song);
        // 将结果存放map中
        result.put("success", success);
        // 返回json格式的字符串
        return jsonTool.writeValueAsString(result);
    }

    @RequestMapping("/delete")
    public String delete(Integer id) throws JsonProcessingException {
        //map集合【键值对】
        HashMap result = new HashMap<>();
        // 执行删除
        boolean delete = songService.removeById(id);
        // 将结果存map中
        result.put("success", delete);
        //返回json格式的数据【字符串】
        return jsonTool.writeValueAsString(result);
    }
}
