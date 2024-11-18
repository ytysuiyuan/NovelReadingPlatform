package com.example.novelreading.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.novelreading.entity.Comment;
import com.example.novelreading.mapper.CommentMapper;
import com.example.novelreading.service.ICommentService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements ICommentService {

    @Resource
    private CommentMapper commentMapper;

    @Override
    public boolean addComment(Integer userId, Integer novelId, String content){
        Comment comment=new Comment();
        comment.setUserId(userId);
        comment.setNovelId(novelId);
        comment.setContent(content);
        return commentMapper.insert(comment)>0;
    }

    @Override
    public boolean deleteComment(Integer id){
        return commentMapper.deleteById(id)>0;
    }

    @Override
    public boolean deleteCommentByUserId(Integer userId){
        return commentMapper.deleteCommentByUserId(userId)>0;
    }

    @Override
    public boolean deleteCommentByNovelId(Integer novelId){
        return commentMapper.deleteCommentByUserId(novelId)>0;
    }

    @Override
    public PageInfo<Comment> getAllComments(int pageNo, int pageSize){
        PageHelper.startPage(pageNo,pageSize);
        QueryWrapper<Comment> queryWrapper=new QueryWrapper<>();
        queryWrapper.orderByDesc("created_at");
        List<Comment> commentList=commentMapper.selectList(queryWrapper);
        PageInfo<Comment> pageInfo=new PageInfo<>(commentList);
        return pageInfo;
    }

    @Override
    public PageInfo<Comment> getAllCommentsById(int userId, int pageNo, int pageSize){
        PageHelper.startPage(pageNo,pageSize);
        QueryWrapper<Comment> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("user_id",userId);
        queryWrapper.orderByDesc("created_at");
        List<Comment> commentList=commentMapper.selectList(queryWrapper);
        PageInfo<Comment> pageInfo=new PageInfo<>(commentList);
        return pageInfo;
    }
}
