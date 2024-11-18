package com.example.novelreading.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.novelreading.entity.Comment;
import com.github.pagehelper.PageInfo;

public interface ICommentService extends IService<Comment> {

    boolean addComment(Integer userId, Integer novelId, String content);

    boolean deleteComment(Integer id);

    boolean deleteCommentByUserId(Integer userId);

    boolean deleteCommentByNovelId(Integer novelId);

    PageInfo<Comment> getAllComments(int pageNo, int pageSize);

    PageInfo<Comment> getAllCommentsById(int userId, int pageNo, int pageSize);
}
