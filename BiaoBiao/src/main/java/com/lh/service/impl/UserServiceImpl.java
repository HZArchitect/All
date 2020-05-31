package com.lh.service.impl;

import com.lh.dao.UserDao;
import org.springframework.stereotype.Service;
import com.lh.entity.User;
import com.lh.service.UserService;

import javax.annotation.Resource;

@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserDao userDao;

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    public User getUser(User user) {
        return userDao.getUser(user);
    }
}
