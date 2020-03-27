package com.example.demo.service;

import com.example.demo.dao.UserDao;
import com.example.demo.entity.Response;
import com.example.demo.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigurationPackage;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

@Service
public class FileService {
    @Autowired
    private SessionService sessionService;

    //检查是否建立了用户在datafile里的顶层文件夹，没建立就新建并再新建下层source和result两个文件夹，理论上一定能成功的。正常返回的Response会把这个检查过的绝对路径放在Error里。
    public Response checkUserFolder(HttpSession session){
         Response response = new Response();
         //System.out.println(session.getAttribute("id"));
         if(!Objects.equals(sessionService.auth(session).getStatus(), "200")) {
             return sessionService.auth(session);
         }
        //获取当前绝对路径
        File directory = new File("");//参数为空
        String cur_dir;
        try {
            cur_dir = directory.getCanonicalPath();
        } catch (IOException e) {
            e.printStackTrace();
            response.setStatus("503");
            response.setError("新建文件夹失败!");
            return response;
        }
        //合成新建文件夹路径
        String userFolder= cur_dir+"\\src\\datafile\\" + session.getAttribute("id").toString();
        File file =new File(userFolder);
        if(!file.exists()){
            file.mkdir();
        }

        //以上确保用户有自己的文件夹
        response.setError(userFolder);
        return response;
    }

    //检查任何路径的文件夹，已创建则返回1，未创建返回0并尝试创建.
    public int checkAnyFolder(String path){
        File file =new File(path);
        if(!file.exists()){
            file.mkdir();
            return 0;
        }
        return 1;
    }

    //检查任何路径的文件，已创建则返回1，未创建返回0.
    public int checkAnyFile(String path){
        File file =new File(path);
        if(!file.exists()){
            return 0;
        }
        else return 1;
    }



}
