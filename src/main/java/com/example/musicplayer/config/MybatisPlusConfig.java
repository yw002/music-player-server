package com.example.musicplayer.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//定义为一个配置类
@Configuration
@MapperScan("com.example.musicplayer.mapper")
public class MybatisPlusConfig {

    //定义一个工厂方法，返回bean【拦截器】 limit关键字【限制查询】
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        //创建
        MybatisPlusInterceptor mybatisPlusInterceptor = new MybatisPlusInterceptor();
        //Interceptor中添加分页的拦截器
        mybatisPlusInterceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
        //返回
        return mybatisPlusInterceptor;
    }

}