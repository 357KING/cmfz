package com.baizhi.cjw.util;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;

public class HttpUtil {
    public static String getHttpAndUpload(MultipartFile file, HttpServletRequest request,String path){
        //获取真实路径
        String realPath = request.getSession().getServletContext().getRealPath(path);
        //防止重名
        String newPath = new Date().getTime() + "_" +file.getOriginalFilename();
        //上传文件
        try {
            file.transferTo(new File(realPath,newPath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        //获取网络路径
        //获取协议头
        String scheme = request.getScheme();
        //获取ip
        String localhost=null;
        try {
           localhost = InetAddress.getLocalHost().toString();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        //获取端口
        int serverPort = request.getServerPort();
        //获取项目名
        String contextPath = request.getContextPath();
        String http = scheme + "://" + localhost.split("/")[1] + ":" + serverPort + contextPath + path + newPath;
        return http;
    }
}
