package com.example.novelreading.controller;

import com.example.novelreading.common.result.Result;
import com.example.novelreading.entity.Favorite;
import com.example.novelreading.service.IFavoriteService;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/favorite")
public class FavoriteController {

    @Resource
    private IFavoriteService favoriteService;

    @PostMapping("/addById")
    public Result addCollection(@RequestBody Favorite favorite){
        try{
            Integer userId=favorite.getUserId();
            Integer novelId=favorite.getNovelId();
            if(favoriteService.addCollection(userId, novelId)){
                return Result.ok("添加收藏成功");
            }
            else {
                return Result.error("添加收藏失败");
            }
        }
        catch (Exception e){
            return Result.error("违反约束");
        }
    }

    @GetMapping("/getAllCollectionsById")
    public Result getAllCollectionsById(@RequestParam Integer userId,
                                    @RequestParam(defaultValue = "1") int pageNo,
                                    @RequestParam(defaultValue = "10") int pageSize,
                                        @RequestParam(defaultValue = "true") boolean order){
        try{
            PageInfo<Favorite> pageInfo=favoriteService.getAllCollectionsById(userId,pageNo,pageSize,order);

            // 创建一个包含收藏列表和总页数的结果对象
            Map<String, Object> resultData = new HashMap<>();
            resultData.put("collections", pageInfo.getList()); // 收藏列表
            resultData.put("totalPages", pageInfo.getPages()); // 总页数

            return Result.ok(resultData);
        }
        catch (Exception e){
            return Result.error("Error occurred while getting collections: "+e.getMessage());
        }
    }

    @GetMapping("/getAllCollections")
    public Result getAllCollections(@RequestParam(defaultValue = "1") int pageNo,
                                    @RequestParam(defaultValue = "10") int pageSize){
        try{
            PageInfo<Favorite> pageInfo=favoriteService.getAllCollections(pageNo,pageSize);

            // 创建一个包含收藏列表和总页数的结果对象
            Map<String, Object> resultData = new HashMap<>();
            resultData.put("collections", pageInfo.getList()); // 收藏列表
            resultData.put("totalPages", pageInfo.getPages()); // 总页数

            return Result.ok(resultData);
        }
        catch (Exception e){
            return Result.error("Error occurred while getting collections: "+e.getMessage());
        }
    }

    @DeleteMapping("/deleteCollectionById")
    public Result deleteCollectionById(@RequestParam Integer id){
        if(favoriteService.deleteCollectionById(id)){
            return Result.ok("删除成功");
        }
        else{
            return Result.error("删除失败");
        }
    }

    @GetMapping("/isCollectedByThisUser")
    public Result isCollectedByThisUser(@RequestParam Integer userId,
                                        @RequestParam Integer novelId){
        boolean isCollected=favoriteService.isCollectedByThisUser(userId, novelId);
        if(isCollected){
            return Result.ok("已被该用户收藏",true);
        }
        else{
            return Result.error("没有被收藏",false);
        }
    }

    @CrossOrigin(origins = "http://localhost:5173", methods = { RequestMethod.DELETE, RequestMethod.OPTIONS })
    @DeleteMapping("/deleteCollectionByUser")
    public Result deleteCollectionByUser(@RequestParam Integer userId,
                                         @RequestParam Integer novelId){
        if(favoriteService.deleteCollection(userId, novelId)){
            return Result.ok("删除成功");
        }
        else{
            return Result.error("删除失败");
        }
    }

}
