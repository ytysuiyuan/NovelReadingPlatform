package com.example.novelreading.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.novelreading.entity.Favorite;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface FavoriteMapper extends BaseMapper<Favorite> {
    @Delete("DELETE FROM favorite WHERE user_id = #{userId} AND novel_id = #{novelId}")
    Integer deleteCollection(@Param("userId") Integer userId, @Param("novelId") Integer novelId);
}
