package com.example.musicplayer.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.musicplayer.entity.Singer;
import com.example.musicplayer.service.ISingerService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/singer")
public class SingerController {


    //提供业务层依赖对象
    @Autowired
    ISingerService singerService;

    //json的解析工具
    ObjectMapper jsonTool = new ObjectMapper();

    // http://localhost:8085/music/singer/listALL
    @RequestMapping("/listALL")
    public String listALL() throws JsonProcessingException {
        //定义键值对集合map
        Map<String, Object> result = new HashMap<>();
        //将数据put到map中
        List<Singer> list = singerService.list();
        //返回list、total
        result.put("list", list);
        //返回json格式的数据
        return jsonTool.writeValueAsString(result);
    }

    // http://localhost:8085/music/singer/list?pageNum=2&size=4
    @RequestMapping("/list")
    public String list(@RequestParam(required = false, defaultValue = "") String name,
                       @RequestParam(required = false, defaultValue = "1") Integer pageNum,
                       @RequestParam(required = false, defaultValue = "10") Integer size) throws JsonProcessingException {
        //定义键值对集合map
        Map<String, Object> result = new HashMap<>();
        //构建分页查询对象
        IPage<Singer> page = new Page<>(pageNum, size);
        //构建条件构造器
        QueryWrapper<Singer> wrapper = new QueryWrapper<>();
        wrapper.like(name != null && !name.equals(""), "name", name);
        //将数据put到map中
        IPage<Singer> SingerIPage = singerService.page(page, wrapper);
        List<Singer> list = SingerIPage.getRecords();
        //返回list、total
        result.put("list", list);
        result.put("total", SingerIPage.getTotal());
        //返回json格式的数据
        return jsonTool.writeValueAsString(result);
    }

    //1、根据性别分类查询歌手列表  http://localhost:8085/music/song/singer/sex?sex=2
    @RequestMapping("/sex/detail")
    public String detail(Integer sex) throws JsonProcessingException {
        //map集合【键值对】
        HashMap result = new HashMap<>();
        //查询条件 sex
        QueryWrapper<Singer> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("sex", sex);
        //调用了业务层的查询
        List<Singer> listSongs = singerService.list(queryWrapper);
        // key - value
        result.put("list", listSongs);
        //返回json格式的数据【字符串】
        return jsonTool.writeValueAsString(result);
    }

    @RequestMapping("/update")  // @RequestBody 表示解析json格式的数据【反序列化】
    public String update(@RequestBody Singer singer) throws JsonProcessingException {
        // map
        HashMap<String, Object> result = new HashMap<>();
        // 表单验证
        if (singer.getName() == null || singer.getName().equals("")) {
            result.put("code", -1);//表示失败
            result.put("msg", "姓名格式，不能为空！！");
            return jsonTool.writeValueAsString(result);
        }
        // 执行新增-修改 saveOrUpdate【判断id及记录是否存在，存在则进行修改操作，不存在则新增操作】
        boolean success = singerService.saveOrUpdate(singer);
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
        boolean delete = singerService.removeById(id);
        // 将结果存map中
        result.put("success", delete);
        //返回json格式的数据【字符串】
        return jsonTool.writeValueAsString(result);
    }
}