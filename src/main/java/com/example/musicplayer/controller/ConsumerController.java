package com.example.musicplayer.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.musicplayer.entity.Consumer;
import com.example.musicplayer.service.IConsumerService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/consumer")
public class ConsumerController {
    // 基本路径
    @Value("${music-player.file-path}")
    private String baseFilePath;

    //依赖业务层接口
    @Autowired  // 自动注入
            IConsumerService consumerService;

    // json解析工具
    ObjectMapper jsonTool = new ObjectMapper();

    //注册的请求处理方法-http
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String add(Consumer consumer) throws JsonProcessingException {
        //hashmap
        HashMap result = new HashMap();
        //判断数据的合法、有效性
        if (consumer.getUsername() == null || consumer.getUsername().equals("")) {
            result.put("code", 0);//表示失败
            result.put("msg", "用户账号名格式，不能为空！！");
            return jsonTool.writeValueAsString(result);
        }
        //时间获取
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = dateFormat.format(new Date());
        consumer.setUpdateTime(time);
        consumer.setCreateTime(time);
        //调用业务层来查询是否有用户数据
        boolean save = consumerService.save(consumer);
        //返回结果[判断]
        if (save) {//成功操作
            result.put("code", 1);//表示成功
            result.put("msg", "注册成功");
            result.put("userMsg", consumer);
        } else {
            result.put("code", 0);//表示失败
            result.put("msg", "注册错误");
        }
        //返回json格式
        return jsonTool.writeValueAsString(result);
    }

    //定义一个登录查询用户的请求方法
    @RequestMapping("/login/status")
    public String login(String username, String password) throws JsonProcessingException {
        //响应json格式的数据
        HashMap result = new HashMap();
        //构建条件对象
        QueryWrapper<Consumer> wrapper = new QueryWrapper<>();
        //条件：登录条件
        wrapper.eq("username", username);
        wrapper.eq("password", password);
        //查询操作
        Consumer consumer = consumerService.getOne(wrapper);
        //逻辑判断
        if (consumer != null) {
            //登录成功
            result.put("code", 1);
            result.put("userMsg", consumer);
        } else {
            //登录失败
            result.put("code", -1);
        }
        //返回json格式的String数据
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
        IPage<Consumer> page = new Page<>(pageNum, size);
        // 构建条件构造器
        QueryWrapper<Consumer> wrapper = new QueryWrapper<>();
        // 注入条件
        wrapper.like(name != null && !name.equals(""), "username", name);
        // page方法，注入wrapper
        IPage<Consumer> consumerIPage = consumerService.page(page, wrapper);
        // 获取结果list
        List<Consumer> list = consumerIPage.getRecords();
        // 将结果存放map中
        result.put("list", list);
        result.put("total", consumerIPage.getTotal());
        // 返回json格式的字符串
        return jsonTool.writeValueAsString(result);
    }

    @RequestMapping("/delete")
    public String delete(Integer id) throws JsonProcessingException {
        //map集合【键值对】
        HashMap result = new HashMap<>();
        // 执行删除
        boolean delete = consumerService.removeById(id);
        // 将结果存map中
        result.put("success", delete);
        //返回json格式的数据【字符串】
        return jsonTool.writeValueAsString(result);
    }


    // 【新增或修改】请求处理方法【http请求接口】  http://localhost:8080/consumer/update
    @RequestMapping("/update")  // @RequestBody 表示解析json格式的数据【反序列化】
    public String update(@RequestBody Consumer consumer) throws JsonProcessingException {
        // map
        HashMap<String, Object> result = new HashMap<>();
        // 执行新增-修改 saveOrUpdate【判断id及记录是否存在，存在则进行修改操作，不存在则新增操作】
        boolean success = consumerService.saveOrUpdate(consumer);
        // 将结果存放map中
        result.put("success", success);
        // 返回json格式的字符串
        return jsonTool.writeValueAsString(result);
    }

    //主键查询consumer的请求处理方法-http
    @RequestMapping(value = "/detail", method = RequestMethod.GET)
    public String detail(Integer id) throws JsonProcessingException {
        //hashmap
        HashMap result = new HashMap();
        //根据主键id查询用户
        Consumer consumer = consumerService.getById(id);
        // key - value
        result.put("code", 1);//成功
        //存放登录成功后的对象数据
        result.put("consumer", consumer);
        //返回json格式的数据【字符串】
        return jsonTool.writeValueAsString(result);
    }

    //根据id修改-或者新增数据  @RequestBody将接受到的json格式的数据进行反序列化
    // http://localhost:8085/music/consumer/client/update?username=test&password=123
    @RequestMapping("/client/update")
    public String updateClient(Consumer consumer) throws JsonProcessingException {
        //map键值对集合
        HashMap result = new HashMap<>();
        //非空判断
        if (consumer.getUsername() == null || consumer.getUsername().equals("")) {
            result.put("code", -1);//表示失败
            result.put("msg", "用户账号名格式，不能为空！！");
            return jsonTool.writeValueAsString(result);
        }
        //创建时间
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = dateFormat.format(new Date());
        consumer.setUpdateTime(time);
        //saveOrUpdate方法【新增、修改：根据有没有id进行判断】
        boolean update = consumerService.saveOrUpdate(consumer);
        result.put("code", update ? 1 : -1);//表示失败
        //返回json格式的数据
        return jsonTool.writeValueAsString(result);
    }


    //用户头像修改的请求处理方法-http
    @RequestMapping(value = "/avatar/update", method = RequestMethod.POST)
    public String avatarUpdate(@RequestParam("file") MultipartFile avatarFile, @RequestParam("id") Integer id) throws JsonProcessingException {
        //hashmap
        HashMap result = new HashMap();
        //判断文件是否为空
        if (avatarFile.isEmpty()) {
            result.put("code", 0);//表示失败
            result.put("msg", "文件上传失败！！");
            return jsonTool.writeValueAsString(result);
        }
        //文件名获取
        String fileName = System.currentTimeMillis() + avatarFile.getOriginalFilename();
        System.out.println("fileName=" + fileName);
        String filePath = baseFilePath + "avatarImages/";
        //创建文件对象
        File dest = new File(filePath + fileName);
        //实现文件上传
        try {
            avatarFile.transferTo(dest);//文件上传拷贝的方法transferTo
            //修改用户数据
            Consumer consumer = new Consumer();
            consumer.setId(id);
            consumer.setAvatar("/music/avatarImages/" + fileName);
            //
            boolean update = consumerService.updateById(consumer);
            //返回结果[判断]
            if (update) {//成功操作
                result.put("code", 1);//表示成功
                result.put("msg", "修改头像成功");
                result.put("avatar", "/music/avatarImages/" + fileName);
            } else {
                result.put("code", 0);//表示失败
                result.put("msg", "修改头像错误");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        //返回json格式
        return jsonTool.writeValueAsString(result);
    }
}
