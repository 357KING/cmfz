package com.baizhi.cjw.controller;

import com.alibaba.druid.support.json.JSONUtils;
import com.baizhi.cjw.entity.User;
import com.baizhi.cjw.service.UserService;
import com.baizhi.cjw.util.HttpUtil;
import io.goeasy.GoEasy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.util.*;

@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    UserService userService;

    //查询用户所在地
    @RequestMapping("queryByCity")
    public Map queryByCity(){
        //存放所有用户
        HashMap hashMap = new HashMap();
        //查询所有男生
        List<Map> manList = userService.queryUserByCity("0");
        //查询所有女生
        List<Map> womanList = userService.queryUserByCity("1");
        //将查询到的男生女生存到一个集合中
        hashMap.put("man",manList);
        hashMap.put("woman",womanList);
        return hashMap;
    }

    //根据时间查询
    @RequestMapping("queryUserByTime")
    public Map queryUserByTime(){
        HashMap hashMap = new HashMap();
        ArrayList manList = new ArrayList();
        manList.add(userService.queryUserByTime("0",1));
        manList.add(userService.queryUserByTime("0",7));
        manList.add(userService.queryUserByTime("0",30));
        manList.add(userService.queryUserByTime("0",365));
        ArrayList womanList = new ArrayList();
        womanList.add(userService.queryUserByTime("1",1));
        womanList.add(userService.queryUserByTime("1",7));
        womanList.add(userService.queryUserByTime("1",30));
        womanList.add(userService.queryUserByTime("1",365));
        hashMap.put("man",manList);
        hashMap.put("woman",womanList);
        return hashMap;
    }


    //上传图片
    @RequestMapping("uploadUser")
    public Map uploadUser(String userId,HttpSession session, HttpServletRequest request, MultipartFile photo){
        HashMap hashMap = new HashMap();
        //获取真实路径
        String realPath = session.getServletContext().getRealPath("/upload/img/");
        //判断文件夹是否存在
        File file = new File(realPath);
        if (!file.exists()){
            //多级创建
            file.mkdirs();
        }
        //获取网络路径
        String http = HttpUtil.getHttpAndUpload(photo, request, "/upload/img/");
        User user = new User();
        user.setId(userId);
        user.setPhoto(http);
        userService.update(user);
        hashMap.put("status",200);
        return hashMap;
    }

    //查所有
    @RequestMapping("queryAllUser")
    public Map queryAllUser(Integer page,Integer rows){
        Map map = userService.qqueryAllUser(page, rows);
        return map;
    }

    //编辑
    @RequestMapping("editUser")
    public Map editUser(String[] id,String oper, User user){
        HashMap hashMap = new HashMap();
        if ("edit".equals(oper)){
            userService.update(user);
            hashMap.put("userId",user.getId());
        }else if ("add".equals(oper)){
            String s = UUID.randomUUID().toString().replaceAll("-","");
            user.setId(s);
            userService.insert(user);
            //添加一个用户向goeasy发送一次请求

            GoEasy goEasy = new GoEasy( "http://rest-hangzhou.goeasy.io", "BC-55c2cbbdd73e4c49940147d6a9c6ddc5");
            Map map = queryUserByTime();
            String ss = JSONUtils.toJSONString(map);
            System.out.println("ss = " + ss);
            goEasy.publish("cmfz", ss);
            hashMap.put("userId",s);
        }else {
            userService.delete(id);
        }
        return hashMap;
    }
}
