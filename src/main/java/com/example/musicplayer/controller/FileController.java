package com.example.musicplayer.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.UUID;

@RestController
@RequestMapping("/file")
public class FileController {
    @Value("${music-player.file-path}")
    private String baseFilePath;

    //json的解析工具
    ObjectMapper jsonTool = new ObjectMapper();

    //提供一个文件上传的接口【头像-图片】
    @RequestMapping(value = "/avatar/upload", method = RequestMethod.POST)
    public String uploadImage(@RequestParam("file") MultipartFile file) throws JsonProcessingException {
        String path = baseFilePath + "avatarImages/";
        //文件上传
        HashMap result = upload(file, path);
        //返回文件路径
        return jsonTool.writeValueAsString(result);
    }

    //提供一个文件上传的接口【音乐-文件】  {name: 'food2.jpeg', url: 'imageMogr2/thumbnail/360x360/format'}
    @RequestMapping(value = "/music/upload", method = RequestMethod.POST)
    public String uploadMusic(@RequestParam("file") MultipartFile file) throws JsonProcessingException {
        String path = baseFilePath + "song/";
        //文件上传
        HashMap result = upload(file, path);
        result.put("name", result.get("path"));
        result.put("url", result.get("path"));
        //返回文件路径
        return jsonTool.writeValueAsString(result);
    }

    //提供一个文件上传的接口【音乐-图片】  {name: 'food2.jpeg', url: 'imageMogr2/thumbnail/360x360/format'}
    @RequestMapping(value = "/songPic/upload", method = RequestMethod.POST)
    public String uploadMusicPic(@RequestParam("file") MultipartFile file) throws JsonProcessingException {
        String path = baseFilePath + "img/songPic/";
        //文件上传
        HashMap result = upload(file, path);
        result.put("name", result.get("path"));
        result.put("url", result.get("path"));
        //返回文件路径
        return jsonTool.writeValueAsString(result);
    }

    //提供一个文件上传的接口【歌单-图片】
    @RequestMapping(value = "/songList/upload", method = RequestMethod.POST)
    public String uploadSongListPic(@RequestParam("file") MultipartFile file) throws JsonProcessingException {
        //文件上传的路径
        String path = baseFilePath + "img/songListPic/";
        //文件上传
        HashMap result = upload(file, path);
        //返回文件路径
        return jsonTool.writeValueAsString(result);
    }

    //提供一个文件上传的接口【歌单-图片】
    @RequestMapping(value = "/singer/upload", method = RequestMethod.POST)
    public String uploadSingerPic(@RequestParam("file") MultipartFile file) throws JsonProcessingException {
        //文件上传的路径
        String path = baseFilePath + "img/singerPic/";
        //文件上传
        HashMap result = upload(file, path);
        //返回文件路径
        return jsonTool.writeValueAsString(result);
    }

    //文件上传的方法
    public HashMap upload(MultipartFile file, String path) throws JsonProcessingException {
        //请求结果map的定义
        HashMap result = new HashMap();
        //文件非空校验
        if (file.isEmpty()) {
            result.put("success", false);
            result.put("message", "上传文件为空");
            return result;
        }
        //获取文件名【生成唯一的文件名】 【1、uuid获取文件名 2、获取文件的后缀】
        String fileName = "";
        if (path.contains("song")) {
            fileName = file.getOriginalFilename();
        } else {
            fileName = UUID.randomUUID().toString().replace("-", "") + file.getOriginalFilename();
        }
        //构建文件对象
        File dest = new File(path + fileName);
        //写出文件【上传文件到本地硬盘】
        try {
            file.transferTo(dest);
            result.put("success", true);//表示成功
            //文件上传路径的判断
            if (path.contains("avatarImages")) {
                result.put("path", "/music/avatarImages/" + fileName);
            } else if (path.contains("songListPic")) {
                // 存放歌单图片的路径
                result.put("path", "/music/img/songListPic/" + fileName);
            } else if (path.contains("singerPic")) {
                // 存放歌手图片的路径
                result.put("path", "/music/img/singerPic/" + fileName);
            } else if (path.contains("songPic")) {
                // 存放歌曲封面的路径
                result.put("path", "/music/img/songPic/" + fileName);
            } else if (path.contains("song")) {
                // 存放歌曲文件的路径
                result.put("path", "/music/song/" + fileName);
            } else {
                // 其他路径
                result.put("path", "/music/upload/" + fileName);
            }

        } catch (IOException e) {
            e.printStackTrace();
            result.put("success", false);
            result.put("message", "上传文件失败，请稍后重试");
        }
        //返回文件路径
        return result;
    }
}