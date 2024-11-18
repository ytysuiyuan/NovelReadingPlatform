package com.example.novelreading.controller;

import com.example.novelreading.common.result.Result;
import com.example.novelreading.entity.Comment;
import com.example.novelreading.service.ICommentService;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/comment")
public class CommentController {
    @Resource
    private ICommentService commentService;

    @PostMapping("/addCommentById")
    public Result addCommentById(@RequestBody Comment comment){
        Integer userId=comment.getUserId();
        Integer novelId=comment.getNovelId();
        String content=comment.getContent();
        if(commentService.addComment(userId, novelId, content)){
            return Result.ok("添加成功");
        }
        else{
            return Result.error("添加失败");
        }
    }

    @DeleteMapping("/deleteCommentById")
    public Result deleteCommentById(@RequestParam Integer id){
        if(commentService.deleteComment(id)){
            return Result.ok("删除成功");
        }
        else{
            return Result.error("删除失败");
        }
    }

    @DeleteMapping("/deleteCommentByUserId")
    public Result deleteCommentByUserId(@RequestParam Integer userId){
        if(commentService.deleteCommentByUserId(userId)){
            return Result.ok("删除成功");
        }
        else{
            return Result.error("删除失败");
        }
    }

    @DeleteMapping("/deleteCommentByNovelId")
    public Result deleteCommentByNovelId(@RequestParam Integer novelId){
        if(commentService.deleteCommentByNovelId(novelId)){
            return Result.ok("删除成功");
        }
        else{
            return Result.error("删除失败");
        }
    }

    @GetMapping("/getAllComments")
    public Result getAllComments(@RequestParam(defaultValue = "1") int pageNo,
                                @RequestParam(defaultValue = "10") int pageSize){
        try{
            PageInfo<Comment> pageInfo=commentService.getAllComments(pageNo, pageSize);

            // 创建一个包含评论列表和总页数的结果对象
            Map<String, Object> resultData = new HashMap<>();
            resultData.put("comments", pageInfo.getList()); // 评论列表
            resultData.put("totalPages", pageInfo.getPages()); // 总页数

            return Result.ok(resultData);
        }
        catch (Exception e){
            return Result.error("Error occurred while getting comments: "+e.getMessage());
        }

    }

    @GetMapping("/getAllCommentsById")
    public Result getAllCommentsById(@RequestParam Integer userId,
                                     @RequestParam(defaultValue = "1") int pageNo,
                                     @RequestParam(defaultValue = "10") int pageSize){
        try{
            PageInfo<Comment> pageInfo=commentService.getAllCommentsById(userId,pageNo, pageSize);

            // 创建一个包含评论列表和总页数的结果对象
            Map<String, Object> resultData = new HashMap<>();
            resultData.put("comments", pageInfo.getList()); // 评论列表
            resultData.put("totalPages", pageInfo.getPages()); // 总页数

            return Result.ok(resultData);
        }
        catch (Exception e){
            return Result.error("Error occurred while getting comments: "+e.getMessage());
        }
    }
}
