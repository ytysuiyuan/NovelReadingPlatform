package com.example.novelreading.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.novelreading.entity.Favorite;
import com.example.novelreading.mapper.FavoriteMapper;
import com.example.novelreading.service.IFavoriteService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@Service
public class FavoriteServiceImpl extends ServiceImpl<FavoriteMapper, Favorite> implements IFavoriteService {

    @Resource
    private FavoriteMapper favoriteMapper;

    @Override
    public boolean addCollection(Integer userId,Integer novelId){
        Favorite favorite=new Favorite();
        favorite.setUserId(userId);
        favorite.setNovelId(novelId);
        return favoriteMapper.insert(favorite)>0;
    }

    @Override
    public PageInfo<Favorite> getAllCollectionsById(Integer userId, int pageNo, int pageSize, boolean order){
        PageHelper.startPage(pageNo,pageSize);
        QueryWrapper<Favorite> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("user_id",userId);
        if(order){
            queryWrapper.orderByDesc("collected_at");
        }
        else{
            queryWrapper.orderByAsc("collected_at");
        }
        List<Favorite> favoriteList=favoriteMapper.selectList(queryWrapper);
        PageInfo<Favorite> pageInfo=new PageInfo<>(favoriteList);
        return pageInfo;
    }

    @Override
    public PageInfo<Favorite> getAllCollections(int pageNo, int pageSize){
        PageHelper.startPage(pageNo,pageSize);
        QueryWrapper<Favorite> queryWrapper=new QueryWrapper<>();
        queryWrapper.orderByDesc("collected_at");
        List<Favorite> favoriteList=favoriteMapper.selectList(queryWrapper);
        PageInfo<Favorite> pageInfo=new PageInfo<>(favoriteList);
        return pageInfo;
    }

    @Override
    public boolean deleteCollectionById(Integer id){
        return favoriteMapper.deleteById(id)>0;
    }

    @Override
    public boolean isCollectedByThisUser(Integer userId, Integer novelId){
        QueryWrapper<Favorite> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("user_id",userId);
        queryWrapper.eq("novel_id",novelId);
        List<Favorite> favoriteList=favoriteMapper.selectList(queryWrapper);
        return !favoriteList.isEmpty();
    }

    @Override
    public boolean deleteCollection(Integer userId,Integer novelId){
        return favoriteMapper.deleteCollection(userId, novelId)>0;
    }
}
