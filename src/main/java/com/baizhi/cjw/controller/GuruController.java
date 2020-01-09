package com.baizhi.cjw.controller;

import com.baizhi.cjw.entity.Guru;
import com.baizhi.cjw.service.GuruService;
import com.baizhi.cjw.util.HttpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("guru")
public class GuruController {

    @Autowired
    GuruService guruService;

    //查询所有上师
    @RequestMapping("showAllGuru")
    public List<Guru> showAllGuru(){
        List<Guru> gurus = guruService.showAllGuru();
        return gurus;
    }

    //上传头像
    @RequestMapping("uploadGuru")
    public Map uploadGuru(MultipartFile photo, HttpServletRequest request, HttpSession session,String guruId){
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
        //设置id，获取源文件
        Guru guru = new Guru();
        guru.setId(guruId);
        //重新设置路径
        guru.setPhoto(http);
        guruService.update(guru);
        hashMap.put("staus",200);
        return hashMap;
    }

    @RequestMapping("queryAllGuru")
    public Map queryAllGuru(Integer page,Integer rows){
        Map map = guruService.queryAll(page, rows);
        return map;
    }

    @RequestMapping("editGuru")
    public Map editGuru(Guru guru,String[] id, String oper){
        HashMap hashMap = new HashMap();
        if ("edit".equals(oper)){
            guruService.update(guru);
            hashMap.put("guruId",guru.getId());
        }else if ("add".equals(oper)){
            String s = UUID.randomUUID().toString().replaceAll("-", "");
            guru.setId(s);
            guruService.insert(guru);
            hashMap.put("guruId",s);
        }else {
            guruService.delete(id);
        }
        return hashMap;
    }
}
