package com.example.novelreading.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.novelreading.entity.User;
import com.example.novelreading.mapper.UserMapper;
import com.example.novelreading.service.IUserService;

import com.example.novelreading.utils.JwtUtil;
import com.example.novelreading.utils.SaltUtil;
import lombok.extern.slf4j.Slf4j;

import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.stereotype.Service;


import javax.annotation.Resource;


@Slf4j
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Resource
    private UserMapper userMapper;

    @Override
    public String login(String username, String password) {
        // 根据用户名查询用户
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", username);
        User user = userMapper.selectOne(queryWrapper);

        // 校验密码
        if (user != null) {
            // 使用存储的盐值对输入的密码进行哈希
            Md5Hash md5Hash = new Md5Hash(password, user.getSalt(), 1024);
            if (md5Hash.toHex().equals(user.getPassword())) {
                // 验证成功，生成JWT Token
                User jwtUser = new User();
                jwtUser.setId(user.getId());
                jwtUser.setUsername(user.getUsername());

                // 使用JwtUtil生成Token
                String token = JwtUtil.createJwtTokenByUser(jwtUser);
                log.info("生成了token");

                // 返回JWT Token
                return token;
            }
        }
        // 登录失败，返回null
        return null;
    }


    @Override
    public boolean register(User user) {
        try{
            // 检查用户名是否已存在
            QueryWrapper<User> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("username", user.getUsername());
            User existingUser = userMapper.selectOne(queryWrapper);

            if (existingUser != null) {
                // 用户名已存在，注册失败
                log.warn("用户名已存在: {}", user.getUsername());
                return false;
            }
            // 将昵称默认设置为用户名
            user.setNickname(user.getUsername());
            // 1.生成随机盐
            String salt = SaltUtil.getSalt(8);
            // 2. 将随机盐保存到数据库
            user.setSalt(salt);
            // 3. 明文密码进行md5 + salt + hash散列
            Md5Hash md5Hash = new Md5Hash(user.getPassword(), salt, 1024);
            user.setPassword(md5Hash.toHex());
            userMapper.insert(user);
        }
        catch(Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public String getNickNameById(Integer userId){
        return userMapper.getNickNameById(userId);
    }

    @Override
    public User getUserInformationById(Integer userId) {
        // 使用 selectById 直接查询
        User user = userMapper.selectById(userId);

        // 可以根据需要添加 null 检查，避免空指针异常
        if (user == null) {
            log.warn("未找到用户: userId = {}", userId);
        }

        if (user != null) {
            user.setSalt(null);
            user.setPassword(null);
        }

        return user;
    }

    @Override
    public User getUserByUsername(String username){
        QueryWrapper<User> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("username",username);
        User user=userMapper.selectOne(queryWrapper);
        if(user!=null){
            return user;
        }
        else{
            return null;
        }
    }


}
