package com.example.demo.service;

import com.example.demo.dao.UserDao;
import com.example.demo.entity.Response;
import com.example.demo.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigurationPackage;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    UserDao userDao;

    public Response Exist(String userName){
        Response response=new Response();
        User user=new User();
        user.setUsername(userName);
        if(userDao.queryAll(user).size()==0){
            response.setStatus("404");
            response.setError("用户不存在");
            return response;
        }
        response.setStatus("200");
        return response;

    }


}
