package com.example.novelreading.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.novelreading.entity.Novel;
import com.example.novelreading.entity.User;
import com.example.novelreading.mapper.NovelMapper;
import com.example.novelreading.mapper.UserMapper;
import com.example.novelreading.service.INovelService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;

@Slf4j
@Service
public class NovelServiceImpl extends ServiceImpl<NovelMapper, Novel> implements INovelService {

    @Resource
    private NovelMapper novelMapper;

    @Resource
    private UserMapper userMapper;
    @Override
    public PageInfo<Novel> findByTitleLike(String title, int pageNo, int pageSize){
        PageHelper.startPage(pageNo,pageSize);
        QueryWrapper<Novel> queryWrapper=new QueryWrapper<>();
        queryWrapper.like("title",title);
        queryWrapper.orderByDesc("updated_at");
        List<Novel> novelList=novelMapper.selectList(queryWrapper);
        PageInfo<Novel> pageInfo=new PageInfo<>(novelList);
        return pageInfo;
    }

    @Override
    public PageInfo<Novel> findByGenre(String genre, int pageNo, int pageSize){
        PageHelper.startPage(pageNo,pageSize);
        QueryWrapper<Novel> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("genre",genre);
        queryWrapper.orderByDesc("updated_at");
        List<Novel> novelList=novelMapper.selectList(queryWrapper);
        PageInfo<Novel> pageInfo=new PageInfo<>(novelList);
        return pageInfo;
    }

    @Override
    public boolean deleteById(Integer id){
        return novelMapper.deleteById(id)>0;
    }

    @Override
    public boolean addNovel(Novel novel){
        return novelMapper.insert(novel)>0;
    }

    @Override
    public PageInfo<Novel> getAllNovels(int pageNo, int pageSize){
        PageHelper.startPage(pageNo,pageSize);
        QueryWrapper<Novel> queryWrapper=new QueryWrapper<>();
        queryWrapper.orderByDesc("updated_at");
        List<Novel> novelList=novelMapper.selectList(queryWrapper);
        PageInfo<Novel> pageInfo=new PageInfo<>(novelList);
        return pageInfo;
    }

    @Override
    public PageInfo<String> getNovelContent(Integer novelId, int pageNo, int pageSize) {
        Novel novel = novelMapper.selectById(novelId);
        String content = novel.getContent();

        int totalLength = content.length();
        int totalPages = (int) Math.ceil((double) totalLength / pageSize);  // 计算总页数

        int start = (pageNo - 1) * pageSize;
        int end = Math.min(start + pageSize, totalLength);

        if (start >= totalLength) {
            return new PageInfo<>(Collections.emptyList());  // 超出内容长度时返回空
        }

        String pageContent = content.substring(start, end);
        List<String> contentList = Collections.singletonList(pageContent);

        PageInfo<String> pageInfo = new PageInfo<>(contentList);
        pageInfo.setPageNum(pageNo);  // 设置当前页码
        pageInfo.setPageSize(pageSize);  // 设置每页大小
        pageInfo.setPages(totalPages);  // 设置总页数
        pageInfo.setTotal(totalLength);  // 设置总内容长度

        return pageInfo;
    }


    @Override
    public String getNovelUrl(Integer novelId){
        return novelMapper.getNovelCoverById(novelId);
    }

    @Override
    public List<Novel> getRandomNovels(int num) {
        // 获取所有小说
        List<Novel> allNovels = novelMapper.selectList(null);

        // 打乱列表顺序
        Collections.shuffle(allNovels);

        // 如果请求数量大于总数，调整为总数
        if (num > allNovels.size()) {
            num = allNovels.size();
        }

        // 选择前 num 本小说
        return allNovels.subList(0, num);
    }
    @Override
    public List<Novel> getRandomNovelsByTag(int num, String genre) {
        QueryWrapper<Novel> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("genre",genre);
        // 获取所有tag相同的小说
        List<Novel> allNovels = novelMapper.selectList(queryWrapper);

        // 打乱列表顺序
        Collections.shuffle(allNovels);

        // 如果请求数量大于总数，调整为总数
        if (num > allNovels.size()) {
            num = allNovels.size();
        }

        // 选择前 num 本小说
        return allNovels.subList(0, num);
    }

    @Override
    public String getAuthorName(Integer novelId) {
        // 1. 通过 novelId 查询小说，获取 authorId
        Novel novel = novelMapper.selectById(novelId);
        if (novel == null) {
            throw new RuntimeException("Novel not found with id: " + novelId);
        }
        Integer authorId = novel.getAuthorId();

        // 2. 通过 authorId 查询用户信息，获取 nickname
        User author = userMapper.selectById(authorId);
        if (author == null) {
            throw new RuntimeException("Author not found with id: " + authorId);
        }

        // 返回作者的昵称
        return author.getNickname();
    }

    @Override
    public Novel getNovelInfoWithoutContent(Integer novelId) {
        return novelMapper.selectNovelInfoWithoutContent(novelId);
    }
}
