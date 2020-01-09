package com.baizhi.cjw.controller;

import com.baizhi.cjw.entity.Admin;
import com.baizhi.cjw.service.AdminService;
import com.baizhi.cjw.util.CreateValidateCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@RestController
@RequestMapping("admin")
public class AdminController {
    @Autowired
    AdminService adminService;

    //登录
    @RequestMapping("loginAdmin")
    public String loginAdmin(Admin admin,String code,HttpSession session){
        //获取验证码
        String serveCode = (String)session.getAttribute("serveCode");


        //判断验证码是否正确
        if (serveCode.equals(code)){
            Admin admin2 = new Admin();
            admin2.setUsername(admin.getUsername());
            //查询用户
            Admin admin1 = adminService.selectOne(admin2);
            //判断用户是否存在
            if (admin1!=null){
                //判断密码是否正确
                if (admin1.getPassword().equals(admin.getPassword())){
                    session.setAttribute("admin",admin);
                    return "登录成功！";
                }else {
                    return "密码错误！";
                }
            }else {
                return "用户不存在！";
            }
        }else{
            return "验证码错误！";
        }
    }

    //退出登录
    @RequestMapping("logoutAdmin")
    public String logoutAdmin(HttpSession session){
        session.removeAttribute("admin");
        return "login";
    }

    //生成验证码
    @RequestMapping("getAdminCode")
    public void getAdminCode(HttpSession session, HttpServletResponse response) throws IOException {
        CreateValidateCode c = new CreateValidateCode();
        //生成随机验证码
        String code = c.getCode();
        //生成图片
        c.write(response.getOutputStream());
        //将验证码存入session
        session.setAttribute("serveCode",code);
    }
}
