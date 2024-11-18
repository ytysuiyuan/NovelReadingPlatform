package com.example.novelreading.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.novelreading.entity.Favorite;
import com.github.pagehelper.PageInfo;

public interface IFavoriteService extends IService<Favorite> {
    boolean addCollection(Integer userId, Integer novelId);

    PageInfo<Favorite> getAllCollectionsById(Integer userId, int pageNo, int pageSize, boolean order);

    PageInfo<Favorite> getAllCollections(int pageNo, int pageSize);

    boolean deleteCollectionById(Integer id);

    boolean isCollectedByThisUser(Integer userId, Integer novelId);

    boolean deleteCollection(Integer userId, Integer novelId);
}
