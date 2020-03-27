package com.example.demo.service;

import com.example.demo.dao.GroupDao;
import com.example.demo.entity.Group;
import com.example.demo.entity.Response;
import com.example.demo.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigurationPackage;
import org.springframework.stereotype.Service;

@Service
public class GroupService {
    @Autowired
    GroupDao groupDao;

    public Response Exist(String name){
        Response response=new Response();
        Group group=new Group();
        group.setName(name);
        if(groupDao.queryAll(group).size()==0){
            response.setStatus("404");
            response.setError("组不存在");
            return response;
        }
        response.setStatus("200");
        return response;

    }


}
