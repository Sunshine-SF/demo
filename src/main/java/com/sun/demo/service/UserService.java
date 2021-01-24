package com.sun.demo.service;

import com.sun.demo.entity.User;
import com.sun.demo.entity.UserVO;

import java.util.List;

public interface UserService {
    User getUserById(Integer id);

    List<User> getAll();

    int updateUser(UserVO userVO);

    int addUser(UserVO userVO);

    int delUser(List<User> list);

    int delById(int id);

    List<User> queryList(/*int page,int limit,*/String username, String recoveryTime, String reviewTime,String reviewDay,String ssDay);


}
