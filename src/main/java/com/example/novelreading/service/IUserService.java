package com.example.novelreading.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.novelreading.entity.User;

public interface IUserService extends IService<User> {


    String login(String username, String password);

    boolean register(User user);

    String getNickNameById(Integer userId);

    User getUserInformationById(Integer userId);

    User getUserByUsername(String username);
}
