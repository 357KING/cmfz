package com.baizhi.cjw.controller;


import com.baizhi.cjw.entity.Album;
import com.baizhi.cjw.service.AlbumService;
import com.baizhi.cjw.util.HttpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("album")
public class AlbumController {

    @Autowired
    AlbumService albumService;

    //上传图片
    @RequestMapping("uploadAlbum")
    public Map uploadAlbum(HttpServletRequest request,HttpSession session, MultipartFile status, String albumId){
        HashMap hashMap = new HashMap();
        //获取真实路径
        String realPath = session.getServletContext().getRealPath("/upload/img/");
        File file = new File(realPath);
        //判断该文件夹是否存在
        if (!file.exists()){
            //多级创建
            file.mkdirs();
        }
        //获取网络路径,上传图片
        String uri = HttpUtil.getHttpAndUpload(status, request, "/upload/img/");
        Album album = new Album();
        //设置id，找到原始数据
        album.setId(albumId);
        //重新设置路径
        album.setStatus(uri);
        albumService.update(album);
        hashMap.put("state",200);
        return hashMap;
    }
    //分页查询
    @RequestMapping("queryAll")
    public Map queryAll(Integer page,Integer rows){
        Map map = albumService.queryAll(page, rows);
        return map;
    }

    //编辑（添加，修改，删除）
    @RequestMapping("editAlbum")
    public Map editAlbum(Album album,String oper, String[] id){
        HashMap hashMap = new HashMap();
        if ("edit".equals(oper)){
            System.out.println("album = " + album);
            albumService.update(album);
            hashMap.put("albumId",album.getId());
        }else if ("add".equals(oper)){
            String s = UUID.randomUUID().toString().replaceAll("-", "");
            album.setId(s);
            albumService.insert(album);
            hashMap.put("albumId",s);
        }else if ("del".equals(oper)){
            albumService.delete(id);
        }
        return hashMap;
    }
}
