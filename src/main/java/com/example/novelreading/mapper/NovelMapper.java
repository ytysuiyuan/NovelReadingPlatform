package com.example.novelreading.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.novelreading.entity.Novel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface NovelMapper extends BaseMapper<Novel> {
    @Select("SELECT cover FROM novel WHERE id = #{novelId}")
    String getNovelCoverById(@Param("novelId") Integer novelId);

    @Select("SELECT id, title, author_id, genre, price, cover, created_at, updated_at FROM novel WHERE id = #{novelId}")
    Novel selectNovelInfoWithoutContent(Integer novelId);

}
