package com.example.musicplayer.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

//本地文件的映射    虚拟url映射本地的文件路径
@Configuration
public class LocalResourceConfig implements WebMvcConfigurer {
    @Value("${music-player.file-path}")
    private String baseFilePath;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String filePrefix = "file:" + baseFilePath;
        registry.addResourceHandler("/avatarImages/**").addResourceLocations(filePrefix + "avatarImages/");
        registry.addResourceHandler("/img/songPic/**").addResourceLocations(filePrefix + "img/songPic/");
        registry.addResourceHandler("/img/**").addResourceLocations(filePrefix + "img/");
        registry.addResourceHandler("/song/**").addResourceLocations(filePrefix + "song/");
        registry.addResourceHandler("/img/songListPic/**").addResourceLocations(filePrefix + "img/songListPic/");
    }
}