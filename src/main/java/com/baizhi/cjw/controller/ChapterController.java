package com.baizhi.cjw.controller;

import com.baizhi.cjw.entity.Chapter;
import com.baizhi.cjw.service.ChapterService;
import com.baizhi.cjw.util.HttpUtil;
import org.apache.commons.io.FileUtils;
import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.AudioHeader;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.audio.mp3.MP3AudioHeader;
import org.jaudiotagger.tag.TagException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("chapter")
public class ChapterController {
    @Autowired
    ChapterService chapterService;



    //下载
    @RequestMapping("downChapter")
    public  void downChapter(HttpServletResponse response,HttpSession session, String url) throws IOException {
        //获取文件路径
        String realPath = session.getServletContext().getRealPath("/upload/music/");
        //处理路径,获取文件名
        String[] split = url.split("/");
        String name = split[split.length - 1];
        //找到文件
        File file = new File(realPath, name);
        //通过url获取本地文件
        response.setHeader("Content-Disposition", "attachment; filename="+name);
        ServletOutputStream outputStream = response.getOutputStream();
        FileUtils.copyFile(file,outputStream);
    }

    //上传音频
    @RequestMapping("uploadChapter")
    public Map uploadChapter(String chapterId,MultipartFile url, HttpServletRequest request, HttpSession session) throws TagException, ReadOnlyFileException, CannotReadException, InvalidAudioFrameException, IOException {
        HashMap hashMap = new HashMap();
        //获取真实路径
        String realPath = session.getServletContext().getRealPath("/upload/music/");
        //判断该文件夹是否存在
        File file = new File(realPath);
        if (!file.exists()){
            //多级创建
            file.mkdirs();
        }
        //获取网络路径，上传文件
        String http = HttpUtil.getHttpAndUpload(url, request, "/upload/music/");
        Chapter chapter = new Chapter();
        chapter.setId(chapterId);
        chapter.setUrl(http);
        //计算文件大小
        Double size = Double.valueOf(url.getSize()/1024/1024);
        chapter.setSize(size);
        //计算音频时长
        // 使用三方计算音频时间工具类 得出音频时长
        String[] split = http.split("/");
        //获取文件名
        String name = split[split.length - 1];
        //通过文件获取AudioFile对象   音频解析对象
        AudioFile read = AudioFileIO.read(new File(realPath, name));
        // 通过音频解析对象 获取 头部信息 为了信息更准确 需要将AudioHeader转换为MP3AudioHeader
        MP3AudioHeader audioHeader = (MP3AudioHeader) read.getAudioHeader();
        //获取音频时长
        int trackLength = audioHeader.getTrackLength();
        //设置音频时长格式为xx分xx秒
        String time = trackLength/60+"分"+trackLength%60+"秒";
        chapter.setTime(time);
        chapterService.update(chapter);
        hashMap.put("status",200);
        return hashMap;
    }

    //分页查所有
    @RequestMapping("queryAll")
    public Map queryAll(Integer page,Integer rows,String albumId){
        Map map = chapterService.queryAll(page, rows,albumId);
        return map;
    }

    //编辑（添加，修改，删除）
    @RequestMapping("editChapter")
    public Map editChapter(Chapter chapter,String oper,String albumId,String[] id){
        HashMap hashMap = new HashMap();
        if ("edit".equals(oper)){
            chapterService.update(chapter);
            hashMap.put("chapterId",chapter.getId());
        }else if ("add".equals(oper)){
            String s = UUID.randomUUID().toString().replaceAll("-", "");
            chapter.setId(s);
            chapter.setAlbumId(albumId);
            chapterService.insert(chapter);
            hashMap.put("chapterId",s);
        }else {
            chapterService.delete(id);
        }
        return hashMap;
    }

}
