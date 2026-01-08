package com.example.musicplayer.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.musicplayer.entity.Comment;
import com.example.musicplayer.mapper.CommentMapper;
import com.example.musicplayer.service.ICommentService;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements ICommentService {

}
