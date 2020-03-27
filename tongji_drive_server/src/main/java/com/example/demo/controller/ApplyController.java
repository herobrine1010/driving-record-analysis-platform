//package com.example.demo.controller;
//
//import com.example.demo.entity.Response;
//import com.example.demo.entity.User;
//import com.example.demo.entity.Apply;
//import com.example.demo.service.SessionService;
//import com.example.demo.service.UserService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.stereotype.Controller;
//import org.springframework.transaction.interceptor.TransactionAspectSupport;
//import org.springframework.util.DigestUtils;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.multipart.MultipartFile;
//import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpSession;
//import javax.validation.constraints.NotNull;
//import java.io.File;
//import java.io.IOException;
//import java.text.DateFormat;
//import java.text.SimpleDateFormat;
//import java.util.Arrays;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//import java.util.Objects;
//
//import com.example.demo.dao.UserDao;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import sun.reflect.annotation.ExceptionProxy;
//
//@RestController
//public class LoginController {
//    @Autowired
//    private UserDao userDao;
//    @Autowired
//    private UserService userService;
//    @Autowired
//    private SessionService sessionService;
//
//
//    @RequestMapping(value = "/login", method = RequestMethod.POST)
//    public Response<Integer> login(@RequestBody User user, HttpSession session) {
//        Response<Integer> response = new Response();
//
//        if (!Objects.equals(userService.Exist(user.getUsername()).getStatus(), "200")) {
//            return userService.Exist(user.getUsername());
//        }
//        //List <Users> users= usersMapper.SELECT();
//        List<User> users = userDao.queryAll(user);
//        if (users.size() == 0) {
//            response.setStatus("403");
//            response.setError("Wrong password !");
//            return response;
//        }
//
//        session.setMaxInactiveInterval(-1);
//
//        session.setAttribute("id", users.get(0).getId());
//
//        //System.out.println(session.getAttribute("id"));
//
//        response.setStatus("200");
//        List<Integer> l =new ArrayList<>();
//        l.add(users.get(0).getId());
//        response.setResult(l);
//        response.setError(session.getId());
//        return response;
//    }
//
//
//    @RequestMapping(value = "/logout", method = RequestMethod.POST)
//    public Response logout(HttpSession session) {
//        //System.out.println(session.getMaxInactiveInterval());
//        Response response = new Response();
//        session.removeAttribute("id");
//        response.setStatus("200");
//        return response;
//    }
//
//
//    @RequestMapping(value = "/register",method =RequestMethod.POST)
//    public Response register(@RequestBody User user){
//        Response response = new Response();
//        //もしユーザー名は他人に使ってしまた
//        if (Objects.equals(userService.Exist(user.getUsername()).getStatus(), "200")) {
//            response.setStatus("403");
//            response.setError("The name you submitted was already registered. Please use a different one!");
//            return response;
//        }
//        try {
//            userDao.insert(user);
//        } catch (Exception e) {
//            e.printStackTrace();
//            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
//            response.setStatus("500");
//            response.setError("Error: Information format of noncompliance with requirements.");
//            return response;
//        }
//        response.setStatus("200");
//        response.setError("Registration successful!");
//
//        return response;
//    }
//
//    @RequestMapping(value="/updateInfo",method=RequestMethod.POST)
//    public Response updateInfo(@RequestBody User user,HttpSession session){
//
//        Response response = new Response();
//        //System.out.println(session.getAttribute("id"));
//        if(!Objects.equals(sessionService.auth(session).getStatus(), "200")) {
//            return sessionService.auth(session);
//        }
//        user.setId(Integer.parseInt(session.getAttribute("id").toString()));
//        // 以上は必要的なコードである。（セッション確認）
//        // これからの部分はユーザー·アカウントをチェックすることです。
//        User user1 =new User();
//        user1.setId((int) session.getAttribute("id"));
//        if(userDao.queryAll(user1).isEmpty()){
//            response.setStatus("403");
//            response.setError("");
//            return response;
//        }
//        //普段使いに省略するでも問題を齎させず。
//
//        try {
//            userDao.update(user);
//        } catch (Exception e) {
//            e.printStackTrace();
////            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
//            response.setStatus("500");
//            response.setError("Error: Information format of noncompliance with requirements.");
//            return response;
//        }
//        response.setStatus("200");
//        response.setError("Account information updated successfully!");
//        return response;
//
//    }
//
//    @RequestMapping(value="/updateAvatar",method = RequestMethod.POST)
//    public Response updateAvatar(@RequestParam("file") MultipartFile avatarFile, HttpSession session){
//        Response response = new Response();
//        //System.out.println(session.getAttribute("id"));
//        if(!Objects.equals(sessionService.auth(session).getStatus(), "200")) {
//            return sessionService.auth(session);
//        }
//
//        int thisUserId=Integer.parseInt(session.getAttribute("id").toString());
//        //ちょっと後で使われるべきです。
//
//        if (avatarFile.isEmpty()) {
//            response.setError("upload failed");
//            response.setStatus("500");
//            return response;
//        }
//
//        String fileName=avatarFile.getOriginalFilename();
//        //より良い書き方：
//        //String fileName = System.currentTimeMillis()+avatarFile.getOriginalFilename();
//
//        String filePath = System.getProperty("user.dir") + System.getProperty("file.separator") + "avatarImages" ;
//        File file1 = new File(filePath);
//        if (!file1.exists()){
//            file1.mkdir();
//        }
//
//        File dest = new File(filePath + System.getProperty("file.separator") + fileName);
//        String storeAvatarPath = "/avatarImages/"+fileName;
//        try {
//            avatarFile.transferTo(dest);
//            User user =new User();
//            user.setId(thisUserId);
//            user.setAvatar(storeAvatarPath);
//            userDao.update(user);
//
//        }
//        catch (IOException e){
//            response.setError("upload failed");
//            response.setStatus("403");
//            return response;
//        }
//        response.setError("upload successful!");
//        response.setStatus("200");
//        return response;
//
//    }
//
//
//
//}
