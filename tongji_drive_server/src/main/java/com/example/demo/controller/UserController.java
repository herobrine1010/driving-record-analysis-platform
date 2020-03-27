package com.example.demo.controller;


import com.example.demo.dao.*;
import com.example.demo.entity.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.example.demo.service.FileService;
import com.example.demo.service.SessionService;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.net.ssl.HttpsURLConnection;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RestController
public class UserController {
    @Autowired
    private UserDao userDao;
    @Autowired
    private GroupDao groupDao;
    @Autowired
    private RecordDao recordDao;
    @Autowired
    private UploadsDao uploadsDao;
    @Autowired
    private IsOfGroupDao isOfGroupDao;
    @Autowired
    private HasGroupDao hasGroupDao;
    @Autowired
    private UserService userService;
    @Autowired
    private SessionService sessionService;
    @Autowired
    private FileService fileService;


    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public Response<Integer> login(@RequestBody User user, HttpSession session) {
        Response<Integer> response = new Response();

        if (!Objects.equals(userService.Exist(user.getUsername()).getStatus(), "200")) {
            return userService.Exist(user.getUsername());
        }
        //List <Users> users= usersMapper.SELECT();
        List<User> users = userDao.queryAll(user);
        if (users.size() == 0) {
            response.setStatus("403");
            response.setError("Wrong password !");
            return response;
        }

        session.setMaxInactiveInterval(-1);

        session.setAttribute("id", users.get(0).getId());

        //System.out.println(session.getAttribute("id"));

        response.setStatus("200");
        List<Integer> l =new ArrayList<>();
        l.add(users.get(0).getId());
        response.setResult(l);
        response.setError(session.getId());
        return response;
    }


    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    public Response logout(HttpSession session) {
        //System.out.println(session.getMaxInactiveInterval());
        Response response = new Response();
        session.removeAttribute("id");
        response.setStatus("200");
        return response;
    }


    @RequestMapping(value = "/register",method =RequestMethod.POST)
    public Response register(@RequestBody User user){
        Response response = new Response();
        //もしユーザー名は他人に使ってしまた
        if (Objects.equals(userService.Exist(user.getUsername()).getStatus(), "200")) {
            response.setStatus("403");
            response.setError("The name you submitted was already registered. Please use a different one!");
            return response;
        }
        try {
            userDao.insert(user);
        } catch (Exception e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            response.setStatus("500");
            response.setError("Error: Information format of noncompliance with requirements.");
            return response;
        }
        response.setStatus("200");
        response.setError("Registration successful!");

        return response;
    }

    @RequestMapping(value="/updateInfo",method=RequestMethod.POST)
    public Response updateInfo(@RequestBody User user,HttpSession session){

        Response response = new Response();
        //System.out.println(session.getAttribute("id"));
        if(!Objects.equals(sessionService.auth(session).getStatus(), "200")) {
            return sessionService.auth(session);
        }
        user.setId(Integer.parseInt(session.getAttribute("id").toString()));
        // 以上は必要的なコードである。（セッション確認）
        // これからの部分はユーザー·アカウントをチェックすることです。
        User user1 =new User();
        user1.setId((int) session.getAttribute("id"));
        if(userDao.queryAll(user1).isEmpty()){
            response.setStatus("403");
            response.setError("");
            return response;
        }
        //普段使いに省略するでも問題を齎させず。

        try {
            userDao.update(user);
        } catch (Exception e) {
            e.printStackTrace();
//            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            response.setStatus("500");
            response.setError("Error: Information format of noncompliance with requirements.");
            return response;
        }
        response.setStatus("200");
        response.setError("Account information updated successfully!");
        return response;

    }

    @RequestMapping(value="/updateAvatar",method = RequestMethod.POST)
    public Response updateAvatar(@RequestParam("file") MultipartFile avatarFile, HttpSession session){
        Response response = new Response();
        //System.out.println(session.getAttribute("id"));
        if(!Objects.equals(sessionService.auth(session).getStatus(), "200")) {
            return sessionService.auth(session);
        }

        int thisUserId=Integer.parseInt(session.getAttribute("id").toString());
        //ちょっと後で使われるべきです。

        if (avatarFile.isEmpty()) {
            response.setError("upload failed");
            response.setStatus("500");
            return response;
        }

        String fileName=avatarFile.getOriginalFilename();
        //より良い書き方：
        //String fileName = System.currentTimeMillis()+avatarFile.getOriginalFilename();

        String filePath = System.getProperty("user.dir") + System.getProperty("file.separator") + "avatarImages" ;
        File file1 = new File(filePath);
        if (!file1.exists()){
            file1.mkdir();
        }

        File dest = new File(filePath + System.getProperty("file.separator") + fileName);
        String storeAvatarPath = "/avatarImages/"+fileName;
        try {
            avatarFile.transferTo(dest);
            User user =new User();
            user.setId(thisUserId);
            user.setAvatar(storeAvatarPath);
            userDao.update(user);

        }
        catch (IOException e){
            response.setError("upload failed");
            response.setStatus("403");
            return response;
        }
        response.setError("upload successful!");
        response.setStatus("200");
        return response;

    }

    @RequestMapping(value = "/createGroup",method =RequestMethod.POST)
    public Response createGroup(@RequestBody Group group){
        Response response = new Response();
        //もしユーザー名は他人に使ってしまた
        if (Objects.equals(userService.Exist(group.getName()).getStatus(), "200")) {
            response.setStatus("403");
            response.setError("组已存存在，请尝试更换组名！");
            return response;
        }
        try {
            groupDao.insert(group);
        } catch (Exception e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            response.setStatus("500");
            response.setError("Error: Information format of noncompliance with requirements.");
            return response;
        }
        response.setStatus("200");
        response.setError("成功创建组!");

        return response;
    }

    @RequestMapping(value="/updateGroup",method=RequestMethod.POST)
    public Response updateGroup(@RequestBody Group group,HttpSession session){
        Response response = new Response();
        //System.out.println(session.getAttribute("id"));
        if(!Objects.equals(sessionService.auth(session).getStatus(), "200")) {
            return sessionService.auth(session);
        }
        int thisUserId=Integer.parseInt(session.getAttribute("id").toString());

        Group group1=new Group();
        group1.setName(group.getName());
        if(groupDao.queryAll(group1).isEmpty()){
            response.setStatus("403");
            response.setError("没有对应组！");
            return response;
        }
        else{
            group1=groupDao.queryAll(group1).get(0);
        }

        HasGroup hasGroup1=new HasGroup();
        hasGroup1.setGroupId(group1.getId());
        hasGroup1.setUserId(thisUserId);
        if(hasGroupDao.queryAll(hasGroup1).isEmpty()){
            response.setStatus("405");
            response.setError("您不是该组的管理员，无法操作！");
            return response;
        }

        try {
            groupDao.update(group);
        } catch (Exception e) {
            e.printStackTrace();
//            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            response.setStatus("500");
            response.setError("Error: Information format of noncompliance with requirements.");
            return response;
        }
        response.setStatus("200");
        response.setError("组信息成功更新！");
        return response;

    }
    /*命名规范!!!：
     *   上传文件:新建 "记录id"文件夹,下建"source"文件夹,存放:前向视频："front.mp4",司机向视频："face.mp4",运动学数据："kinematics.csv"
     *   生成文件:在"记录id"文件夹下建立"result"文件夹
     * */
    @RequestMapping(value="/uploadFrontMp4",method=RequestMethod.POST)
    public Response uploadFrontMp4(HttpSession session){
        Response response = new Response();
        //System.out.println(session.getAttribute("id"));
        if(!Objects.equals(sessionService.auth(session).getStatus(), "200")) {
            return sessionService.auth(session);
        }
        //fileService.checkUserFolder(session);//检查用户顶级文件夹
        String FrontMp4Path=fileService.checkUserFolder(session).error+"\\source\\front.mp4";
        System.out.println(FrontMp4Path);
        response.setError(fileService.checkAnyFile(FrontMp4Path)+"");

        return response;
    }

//    @RequestMapping(value="/joinGroup",method=RequestMethod.POST)
//    public Response joinGroup(@Repository Group group,HttpSession)

    //Record现在加上了status列，"generated"是默认值，直到所有文件上传完毕变为"uploaded",生成result文件后变为"finished".

    //查看当前用户是否有未上传完成的记录，如果有则返回之，如果没有则：
    //新建record行和uploads行并返回record;设置3个路径;在用户顶级目录下新建record_id文件夹和下层两个文件夹
    @RequestMapping(value="/currentRecord",method = RequestMethod.POST)
    public Response<Record> currentRecord(HttpSession session){
        Response<Record> response = new Response<>();
        //System.out.println(session.getAttribute("id"));
        if(!Objects.equals(sessionService.auth(session).getStatus(), "200")) {
            return sessionService.auth(session);
        }
        int thisUserId=Integer.parseInt(session.getAttribute("id").toString());

        Record record = recordDao.findRecordGenerated(thisUserId);
        if(record==null){

            Record record1=new Record();
            recordDao.rawInsert(record1);//直接insert好像有点问题，所以先建空行再update
            //建文件夹
            String user_root= fileService.checkUserFolder(session).error;
            String record_folder=user_root+"\\"+record1.getId().toString();
            fileService.checkAnyFolder(record_folder);
            fileService.checkAnyFolder(record_folder+"\\result");
            fileService.checkAnyFolder(record_folder+"\\source");
            //在record1中写好文件路径并更新到数据库
            record1.setVideo1Path(record_folder+"\\source\\front.mp4");
            record1.setVideo2Path(record_folder+"\\source\\face.mp4");
            record1.setCsvPath(record_folder+"\\source\\kinematics.csv");
            recordDao.update(record1);
            //System.out.println(record1.getId());

            Uploads uploads=new Uploads();
            uploads.setRecordId(record1.getId());
            uploads.setUserId(thisUserId);
            uploadsDao.insert(uploads);

            response.setStatus("200");
            response.setError("新建行和文件夹");

            List<Record> r=new ArrayList<>();
            r.add(recordDao.queryById(record1.getId()));
            response.setResult(r);
        }
        else{
            response.setStatus("200");
            response.setError("已存在未处理行");
            List<Record> r=new ArrayList<>();
            r.add(record);
            response.setResult(r);
        }
        return response;
    }

    //检查某行record的文件是否上传完毕，是则把status改为uploaded.
    //这段不应该创建任何文件夹.
    @RequestMapping(value="/checkFileUploaded",method=RequestMethod.POST)
    public Response checkFileUploaded(HttpSession session){
        Response response = new Response();
        //System.out.println(session.getAttribute("id"));
        if(!Objects.equals(sessionService.auth(session).getStatus(), "200")) {
            return sessionService.auth(session);
        }
        int thisUserId=Integer.parseInt(session.getAttribute("id").toString());

        Record record = recordDao.findRecordGenerated(thisUserId);
        //找出一条status还是generated的记录

        if(record==null){
            response.setError("没有未上传的文件！");
            response.setStatus("503");
        }
        else{
            String user_root= fileService.checkUserFolder(session).error;//用户顶级文件夹路径
            String record_folder=user_root+"\\"+record.getId().toString();//当前记录的文件夹
            String source_folder=record_folder+"\\source\\";
            if(fileService.checkAnyFile(source_folder+"front.mp4")==1
                    && fileService.checkAnyFile(source_folder+"face.mp4")==1
                    && fileService.checkAnyFile(source_folder+"kinematics.csv")==1){
                record.setStatus("uploaded");
                recordDao.update(record);
                response.setError("文件完备，状态已改变");
                response.setStatus("200");
            }
            else{
                System.out.println(source_folder+"front.mp4");
                response.setError("还缺文件！");
                response.setStatus("404");
            }
        }
        return response;

    }


    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    //下面是文件的上传。如果原来路径上已经有文件则先删除，再传。
    @RequestMapping(value="uploadVideo1",method = RequestMethod.POST)
    public Response uploadVideo1(@RequestParam("file") MultipartFile file, HttpSession session) {
        Response<Record> response = new Response<>();
        //System.out.println(session.getAttribute("id"));
        if(!Objects.equals(sessionService.auth(session).getStatus(), "200")) {
            return sessionService.auth(session);
        }
        int thisUserId=Integer.parseInt(session.getAttribute("id").toString());

        if (file.isEmpty()) {
            response.setStatus("404");
            response.setError("上传失败，文件为空！");
            return response;
        }
        //如果原来有文件
        File dest=new File(recordDao.findRecordGenerated(thisUserId).getVideo1Path());
        if(dest.exists()) {
            try {
                dest.delete();
            } catch (Exception e) {
                response.setStatus("503");
                response.setError("未知错误！");
                return response;
            }
            //String fileName = file.getOriginalFilename();
        }
        try {
            file.transferTo(dest);
            LOGGER.info("上传成功");
            response.setError("上传成功");
            response.setStatus("200");
        } catch (IOException e) {
            LOGGER.error(e.toString(), e);
            response.setError("上传失败");
            response.setStatus("503");
        }
        return response;

    }
    @RequestMapping(value="uploadVideo2",method = RequestMethod.POST)
    public Response uploadVideo2(@RequestParam("file") MultipartFile file, HttpSession session) {
        Response<Record> response = new Response<>();
        //System.out.println(session.getAttribute("id"));
        if(!Objects.equals(sessionService.auth(session).getStatus(), "200")) {
            return sessionService.auth(session);
        }
        int thisUserId=Integer.parseInt(session.getAttribute("id").toString());

        if (file.isEmpty()) {
            response.setStatus("404");
            response.setError("上传失败，文件为空！");
            return response;
        }
        //如果原来有文件
        File dest=new File(recordDao.findRecordGenerated(thisUserId).getVideo2Path());
        if(dest.exists()) {
            try {
                dest.delete();
            } catch (Exception e) {
                response.setStatus("503");
                response.setError("未知错误！");
                return response;
            }
            //String fileName = file.getOriginalFilename();
        }
        try {
            file.transferTo(dest);
            LOGGER.info("上传成功");
            response.setError("上传成功");
            response.setStatus("200");
        } catch (IOException e) {
            LOGGER.error(e.toString(), e);
            response.setError("上传失败");
            response.setStatus("503");
        }
        return response;

    }
    @RequestMapping(value="uploadCsv",method = RequestMethod.POST)
    public Response uploadCsv(@RequestParam("file") MultipartFile file, HttpSession session) {
        Response<Record> response = new Response<>();
        //System.out.println(session.getAttribute("id"));
        if(!Objects.equals(sessionService.auth(session).getStatus(), "200")) {
            return sessionService.auth(session);
        }
        int thisUserId=Integer.parseInt(session.getAttribute("id").toString());

        if (file.isEmpty()) {
            response.setStatus("404");
            response.setError("上传失败，文件为空！");
            return response;
        }
        //如果原来有文件
        File dest=new File(recordDao.findRecordGenerated(thisUserId).getCsvPath());
        if(dest.exists()) {
            try {
                dest.delete();
            } catch (Exception e) {
                response.setStatus("503");
                response.setError("未知错误！");
                return response;
            }
            //String fileName = file.getOriginalFilename();
        }
        try {
            file.transferTo(dest);
            LOGGER.info("上传成功");
            response.setError("上传成功");
            response.setStatus("200");
        } catch (IOException e) {
            LOGGER.error(e.toString(), e);
            response.setError("上传失败");
            response.setStatus("503");
        }
        return response;

    }



}
