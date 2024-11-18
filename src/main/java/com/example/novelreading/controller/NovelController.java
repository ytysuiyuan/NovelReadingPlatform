package com.example.novelreading.controller;

import com.baomidou.mybatisplus.extension.api.R;
import com.example.novelreading.common.result.Result;
import com.example.novelreading.entity.Novel;
import com.example.novelreading.service.INovelService;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/novel")
public class NovelController {

    @Resource
    private INovelService novelService;

    @GetMapping("/searchByTitle")
    public Result searchByTitle(@RequestParam String title,
                                @RequestParam(defaultValue = "1") int pageNo,
                                @RequestParam(defaultValue = "10") int pageSize) {
        try {
            PageInfo<Novel> pageInfo = novelService.findByTitleLike(title, pageNo, pageSize);

            // 创建一个包含小说列表和总页数的结果对象
            Map<String, Object> resultData = new HashMap<>();
            resultData.put("novels", pageInfo.getList()); // 小说列表
            resultData.put("totalPages", pageInfo.getPages()); // 总页数

            return Result.ok(resultData);
        } catch (Exception e) {
            return Result.error("Error occurred while searching for novels: " + e.getMessage());
        }
    }


    @GetMapping("/searchByGenre")
    public Result searchByGenre(@RequestParam String genre,
                               @RequestParam(defaultValue = "1") int pageNo,
                               @RequestParam(defaultValue = "10") int pageSize) {
        try {
            PageInfo<Novel> pageInfo = novelService.findByGenre(genre, pageNo, pageSize);

            // 创建一个包含小说列表和总页数的结果对象
            Map<String, Object> resultData = new HashMap<>();
            resultData.put("novels", pageInfo.getList()); // 小说列表
            resultData.put("totalPages", pageInfo.getPages()); // 总页数

            return Result.ok(resultData);
        } catch (Exception e) {
            return Result.error("Error occurred while searching for novels: " + e.getMessage());
        }
    }

    @DeleteMapping("/deleteById")
    public Result deleteById(@RequestParam Integer id){
        if(novelService.deleteById(id)){
            return Result.ok("删除成功");
        }
        else{
            return Result.error("删除失败");
        }
    }

    @PostMapping("/addNovel")
    public Result addNovel(@RequestBody Novel novel){
        boolean isAdded = novelService.addNovel(novel);
        if (isAdded) {
            return Result.ok("添加成功");
        } else {
            return Result.error("添加失败");
        }
    }

    @GetMapping("/getAllNovels")
    public Result getAllNovels(@RequestParam(defaultValue = "1") int pageNo,
                               @RequestParam(defaultValue = "10") int pageSize){
        try{
            PageInfo<Novel> pageInfo=novelService.getAllNovels(pageNo,pageSize);

            // 创建一个包含小说列表和总页数的结果对象
            Map<String, Object> resultData = new HashMap<>();
            resultData.put("novels", pageInfo.getList()); // 小说列表
            resultData.put("totalPages", pageInfo.getPages()); // 总页数

            return Result.ok(resultData);
        }
        catch (Exception e){
            return Result.error("Error occurred while getting novels: "+e.getMessage());
        }
    }

    @GetMapping("/getContent")
    public Result getContent(@RequestParam Integer novelId,
                             @RequestParam(defaultValue = "1") int pageNo,
                             @RequestParam(defaultValue = "10") int pageSize) {
        PageInfo<String> pageInfo = novelService.getNovelContent(novelId, pageNo, pageSize);

        Map<String, Object> response = new HashMap<>();
        response.put("content", pageInfo.getList());
        response.put("currentPage", pageInfo.getPageNum());
        response.put("pageSize", pageInfo.getPageSize());
        response.put("totalPages", pageInfo.getPages());
        response.put("totalLength", pageInfo.getTotal());

        return Result.ok(response);
    }


    @GetMapping("/getUrl")
    public Result getUrl(@RequestParam Integer novelId){
        String Url=novelService.getNovelUrl(novelId);
        if(Url!=null){
            return Result.ok("获取成功",Url);
        }
        else{
            return Result.error("获取失败");
        }
    }

    @GetMapping("/getRandomNovels")
    public Result getRandomNovels(@RequestParam(defaultValue = "10") int num){
        List<Novel> novelList=novelService.getRandomNovels(num);
        if(!novelList.isEmpty()){
            return Result.ok("获取成功",novelList);
        }
        else {
            return Result.error("获取失败");
        }
    }

    @GetMapping("/getRandomNovelsByTag")
    public Result getRandomNovelsByTag(@RequestParam(defaultValue = "10") int num,
                                       @RequestParam String genre){
        List<Novel> novelList=novelService.getRandomNovelsByTag(num,genre);
        if(!novelList.isEmpty()){
            return Result.ok("获取成功",novelList);
        }
        else {
            return Result.error("获取失败");
        }
    }

    @GetMapping("/getAuthorNameById")
    public Result getAuthorNameById(@RequestParam Integer novelId){
        String nickname=novelService.getAuthorName(novelId);
        if(nickname!=null){
            return Result.ok("获取成功",nickname);
        }
        else{
            return Result.error("获取失败");
        }
    }

    @GetMapping("/getNovelInfoWithoutContent")
    public Result getNovelInfoWithoutContent(@RequestParam Integer novelId){
        if(novelService.getNovelInfoWithoutContent(novelId)!=null){
            return Result.ok("获取成功",novelService.getNovelInfoWithoutContent(novelId));
        }
        else{
            return Result.error("获取失败");
        }
    }
}
