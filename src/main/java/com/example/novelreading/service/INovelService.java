package com.example.novelreading.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.novelreading.entity.Novel;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface INovelService extends IService<Novel> {
    PageInfo<Novel> findByTitleLike(String title, int pageNo, int pageSize);

    PageInfo<Novel> findByGenre(String genre, int pageNo, int pageSize);

    boolean deleteById(Integer id);

    boolean addNovel(Novel novel);

    PageInfo<Novel> getAllNovels(int pageNo, int pageSize);

    PageInfo<String> getNovelContent(Integer novelId, int pageNo, int pageSize);

    String getNovelUrl(Integer novelId);

    List<Novel> getRandomNovels(int num);

    List<Novel> getRandomNovelsByTag(int num, String genre);

    String getAuthorName(Integer novelId);

    Novel getNovelInfoWithoutContent(Integer novelId);
}
