package com.example.novelreading.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.novelreading.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper extends BaseMapper<User> {
    @Select("SELECT nickname FROM user WHERE id = #{userId}")
    String getNickNameById(@Param("userId") Integer userId);
}
