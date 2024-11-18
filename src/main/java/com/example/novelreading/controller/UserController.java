package com.example.novelreading.controller;

import com.example.novelreading.common.result.Result;
import com.example.novelreading.entity.User;
import com.example.novelreading.service.IUserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private IUserService userService;

    @PostMapping("/login")
    public Result login(@RequestBody User user) {
        String username=user.getUsername();
        String password=user.getPassword();
        // 调用UserServiceImpl的login方法获取token
        String token = userService.login(username, password);
        System.out.println("获得了token");

        if (token != null) {
            // 获取用户的id
            User newuser = userService.getUserByUsername(username);
            Map<String, Object> data = new HashMap<>();
            data.put("token", token);
            data.put("userId", newuser.getId());

            return Result.ok("登录成功", data);
        } else {
            return Result.error("登录失败，用户名或密码错误");
        }
    }


    @PostMapping("/register")
    public Result register(@RequestBody User user) {
        String username = user.getUsername();
        String password = user.getPassword();

        // 验证密码是否符合要求（必须包含数字和字母，长度>=8）
        if (!isValidPassword(password)) {
            return Result.error("密码必须包含数字和字母，且长度不能小于8位");
        }

        // 创建新用户对象
        User newUser = new User();
        newUser.setUsername(username);
        newUser.setPassword(password);

        // 调用Service的register方法
        boolean isRegistered = userService.register(newUser);
        if (isRegistered) {
            String token = userService.login(username, password);
            if (token != null) {
                // 获取用户的id
                User newuser = userService.getUserByUsername(username);
                Map<String, Object> data = new HashMap<>();
                data.put("token", token);
                data.put("userId", newuser.getId());

                return Result.ok("注册成功", data);
            } else {
                return Result.error("登录失败, token为null");
            }
        } else {
            return Result.error("注册失败，用户名已存在");
        }
    }

    // 验证密码是否符合要求
    private boolean isValidPassword(String password) {
        // 判断密码是否包含至少一个字母和一个数字，并且长度>=8
        return password.length() >= 8 && password.matches("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]+$");
    }



    @GetMapping("/getNickNameById")
    public Result getNickNameById(@RequestParam Integer userId){
        String nickname= userService.getNickNameById(userId);
        if(nickname!=null){
            return Result.ok("获取成功",nickname);
        }
        else{
            return Result.error("获取失败");
        }
    }

    @GetMapping("/getUserInformationById")
    public Result getUserInformationById(@RequestParam Integer userId){
        User user=userService.getUserInformationById(userId);
        if(user!=null){
            return Result.ok("获取成功",user);
        }
        else{
            return Result.error("获取失败");
        }
    }
}
