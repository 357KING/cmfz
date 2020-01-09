package com.baizhi.cjw.controller;

import com.alibaba.excel.EasyExcel;
import com.baizhi.cjw.entity.Banner;
import com.baizhi.cjw.entity.BannerPageDto;
import com.baizhi.cjw.service.BannerService;
import com.baizhi.cjw.util.BannerListener;
import com.baizhi.cjw.util.HttpUtil;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;

import java.util.*;

@RestController
@RequestMapping("banner")
public class BannerController {

    @Autowired
    BannerService bannerService;

    //轮播图导入
    @RequestMapping("inBanner")
    public String inBanner(MultipartFile banners,HttpSession session){
        String ss = null;
        try {
            String realPath = session.getServletContext().getRealPath("/upload/excel/");
            File file = new File(realPath);
            if (!file.exists()){
                file.mkdirs();
            }
            String name = new Date().getTime() + "_" + banners.getOriginalFilename();
            //文件上传
            banners.transferTo(new File(realPath,name));
            //重新读取文件
            File file1 = new File(realPath, name);
            System.out.println("file1 = " + file1);
            //导入
            EasyExcel.read(file1,Banner.class,new BannerListener()).sheet().doRead();
            ss = "导入成功！";
        }catch (Exception e){
            e.printStackTrace();
            ss =  "导入失败！";
        }
        return ss;

    }

    //轮播图模板
    @RequestMapping("anBanner")
    public String  anBanner(){
        try {
            ArrayList<Banner> list = new ArrayList<>();
            Banner banner = new Banner();
            list.add(banner);
            String fileName = "D:\\桌面\\后期项目\\"+new Date().getTime()+".xlsx";
            EasyExcel.write(fileName,Banner.class).sheet().doWrite(list);
            return "下载模板成功！";
        }catch (Exception e){
            e.printStackTrace();
            return "下载模板失败！";
        }

    }

    //轮播图导出
    @RequestMapping("outBanner")
    public String outBanner(){
        try {
            List<Banner> list = bannerService.queryAll();
            String fileName = "D:\\桌面\\后期项目\\"+new Date().getTime()+".xlsx";
            EasyExcel.write(fileName,Banner.class).sheet().doWrite(list);
            return "ok";
        }catch (Exception e){
            e.printStackTrace();
            return "fail";
        }

    }

    //上传图片
    @RequestMapping("uploadBanner")
    public Map  uploadBanner(MultipartFile url, String bannerId, HttpSession session, HttpServletRequest request){
        HashMap hashMap = new HashMap();
        //获取真实路径
        String realPath = session.getServletContext().getRealPath("/upload/img/");
        //判断该文件夹是否存在
        File file = new File(realPath);
        if (!file.exists()){
            //多级创建
            file.mkdirs();
        }
        //获取网络路径
        String ur = HttpUtil.getHttpAndUpload(url, request, "/upload/img/");
        Banner banner = new Banner();
        //设置id（找到原始数据）
        banner.setId(bannerId);
        //url更新
        banner.setUrl(ur);
        //更新文件路径
        bannerService.update(banner);
        hashMap.put("status",200);
        return hashMap;
    }

    //分页查询
    @RequestMapping("queryByPage")
    public BannerPageDto queryByPage(Integer page, Integer rows, Banner banner){
        return bannerService.queryByPage(banner,page,rows);
    }

    //增删改
    @RequestMapping("save")
    public Map save(Banner banner, String oper,String[] id){
        HashMap hashMap = new HashMap();
        if ("edit".equals(oper)){
            //修改
            bannerService.update(banner);
            hashMap.put("bannerId",banner.getId());
        }else if("add".equals(oper)){
            //添加
            String uid = UUID.randomUUID().toString().replaceAll("-", "");
            banner.setId(uid);
            bannerService.insert(banner);
            hashMap.put("bannerId",uid);
        }else {
            //删除
            bannerService.delete(id);
        }
        return hashMap;

    }

    //查所有
    @RequestMapping("queryAll")
    public List<Banner> queryAll(){
       return bannerService.queryAll();
    }
}
