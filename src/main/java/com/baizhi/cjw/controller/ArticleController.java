package com.baizhi.cjw.controller;

import com.baizhi.cjw.entity.Article;
import com.baizhi.cjw.service.ArticleService;
import com.baizhi.cjw.util.HttpUtil;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("article")
public class ArticleController {

    @Autowired
    ArticleService articleService;


    @RequestMapping("queryAll")
    public Map queryAll(Integer page, Integer rows) {
        Map map = articleService.queryAll(page, rows);
        return map;
    }

    @RequestMapping("delete")
    public void delete(String oper, String[] id) {
        if ("del".equals(oper)) {
            articleService.delete(id);
        }
    }

    //上传文章内添加的图片
    @RequestMapping("uploadImg")
    public Map uploadImg(HttpSession session, HttpServletRequest request, MultipartFile imgFile) {
        // 该方法需要返回的信息 error 状态码 0 成功 1失败   成功时 url 图片路径
        HashMap hashMap = new HashMap();
        //获取真实路径
        String realPath = session.getServletContext().getRealPath("/upload/imgArt/");
        //判断文件夹是否存在
        File file = new File(realPath);
        if (!file.exists()) {
            //多级创建
            file.mkdirs();
        }
        try {
            //获取网络路径
            String http = HttpUtil.getHttpAndUpload(imgFile, request, "/upload/imgArt/");
            hashMap.put("error", 0);
            hashMap.put("url", http);
        } catch (Exception e) {
            hashMap.put("error", 1);
            e.printStackTrace();
        }
        return hashMap;
    }

    //查询文章已上传的所有图片
    @RequestMapping("showAllImg")
    public Map showAllImg(HttpServletRequest request,HttpSession session){
        HashMap hashMap = new HashMap();
        hashMap.put("current_url",request.getContextPath()+"/upload/imgArt/");
        String realPath = session.getServletContext().getRealPath("/upload/imgArt/");
        File file = new File(realPath);
        //获取当前文件夹下的所有文件及目录的绝对路径
        File[] files = file.listFiles();
        //将当前文件夹下的文件数量传给total_count
        hashMap.put("total_count",files.length);
        ArrayList arrayList = new ArrayList();
        for (File file1 : files) {
            HashMap fileMap = new HashMap();
            fileMap.put("is_dir",false);
            fileMap.put("has_file",false);
            fileMap.put("filesize",file1.length());
            fileMap.put("is_photo",true);
            String name = file1.getName();
            String extension = FilenameUtils.getExtension(name);
            fileMap.put("fileType",extension);
            fileMap.put("filename",name);
            //通过字符串拆分获取时间戳
            String time = name.split("_")[0];
            // 创建SimpleDateFormat对象 指定yyyy-MM-dd hh:mm:ss 样式
            //  simpleDateFormat.format() 获取指定样式的字符串(yyyy-MM-dd hh:mm:ss)
            // format(参数)  参数:时间类型   new Date(long类型指定时间)long类型  现有数据:字符串类型时间戳
            // 需要将String类型 转换为Long类型 Long.valueOf(str);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String format = simpleDateFormat.format(new Date(Long.valueOf(time)));
            fileMap.put("datetime",format);
            arrayList.add(fileMap);
        }
        hashMap.put("file_list",arrayList);
        return hashMap;
    }

    //添加，修改
    @RequestMapping("editArticle")
    public Map editArticle(Article article,MultipartFile imgs,HttpSession session,HttpServletRequest request){
        HashMap hashMap = new HashMap();
        //修改路径
        if (article.getId()==null || "".equals(article.getId())){
            String realPath = session.getServletContext().getRealPath("/upload/img/");
            File file = new File(realPath);
            if (!file.exists()){
                file.mkdirs();
            }
            String http = HttpUtil.getHttpAndUpload(imgs, request, "/upload/img/");
            article.setImg(http);
            String s = UUID.randomUUID().toString().replaceAll("-", "");
            article.setId(s);
            //添加
            articleService.insert(article);
            hashMap.put("status",200);
        }else{
            String realPath = session.getServletContext().getRealPath("/upload/img/");
            File file = new File(realPath);
            if (!file.exists()){
                file.mkdirs();
            }
            String http = HttpUtil.getHttpAndUpload(imgs, request, "/upload/img/");
            article.setImg(http);
            System.out.println(article);
            articleService.update(article);
            hashMap.put("status",200);
        }
        return hashMap;
    }

}
