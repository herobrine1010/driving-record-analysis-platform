package com.example.demo.service;

import com.example.demo.entity.Response;
import com.example.demo.entity.User;
import org.springframework.stereotype.Service;
import javax.servlet.http.HttpSession;
import java.util.concurrent.TimeUnit;

@Service
public class SessionService {

    public Response auth(HttpSession session){
//        Users users =new Users();
        Response response =new Response();
        if(session.getAttribute("id")==null){
            response.setStatus("401");
            response.setError("您当前没有登录，无法操作！");
            return response;
        }

        response.setStatus("200");
        return response;
    }
}
