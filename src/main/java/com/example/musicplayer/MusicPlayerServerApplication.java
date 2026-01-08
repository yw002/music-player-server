package com.example.musicplayer;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//扫描mapper接口
@MapperScan("com.example.musicplayer.mapper")
@SpringBootApplication
public class MusicPlayerServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(MusicPlayerServerApplication.class, args);
    }

}
