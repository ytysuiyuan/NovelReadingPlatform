package com.example.novelreading.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.novelreading.entity.Comment;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface CommentMapper extends BaseMapper<Comment> {
    @Delete("DELETE FROM comment WHERE user_id = #{userId}")
    Integer deleteCommentByUserId(@Param("userId") Integer userId);

    @Delete("DELETE FROM comment WHERE novel_id = #{novelId}")
    Integer deleteCommentByNovelId(@Param("novelId") Integer novelId);
}
