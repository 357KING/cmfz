package com.baizhi.cjw.controller;

import com.baizhi.cjw.dao.*;
import com.baizhi.cjw.entity.*;
import com.baizhi.cjw.service.*;
import com.baizhi.cjw.util.SmsUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/front")
public class FrontController {

    @Autowired
    BannerService bannerService;
    @Autowired
    GuruDao guruDao;
    @Autowired
    AlbumService albumService;
    @Autowired
    ArticleService articleService;
    @Autowired
    ChapterService chapterService;
    @Autowired
    UserService userService;
    @Autowired
    BannerDao bannerDao;
    @Autowired
    AlbumDao albumDao;
    @Autowired
    ArticleDao articleDao;
    @Autowired
    CounterDao counterDao;
    @Autowired
    CourseDao courseDao;
    @Autowired
    StringRedisTemplate stringRedisTemplate;

    //1、登录接口
    @RequestMapping("/loginUser")
    public Map loginUser(String phone, String password) {
        HashMap hashMap = new HashMap();
        //根据手机号查询用户
        User user = userService.queryByPhone(phone);
        try {
            //判断用户是否存在
            if (user != null) {
                //判断密码是否正确
                if (password.equals(user.getPassword())) {
                    hashMap.put("status", "200");
                    hashMap.put("message", "登录成功");
                } else {
                    hashMap.put("status", "-200");
                    hashMap.put("message", "密码不正确");
                }
            } else {
                hashMap.put("status", "-200");
                hashMap.put("message", "用户不存在");
            }
        } catch (Exception e) {
            e.printStackTrace();
            hashMap.put("status", "-200");
            hashMap.put("message", "error");
        }
        return hashMap;
    }

    //2、发送验证码
    @RequestMapping("/sendCode")
    public Map sendCode(String phone) {
        HashMap hashMap = new HashMap();
        try {
            String uid = UUID.randomUUID().toString();
            String code = uid.substring(0, 3);
            System.out.println("code = " + code);
            SmsUtil.send(phone, code);
            // 将验证码保存值Redis  Key phone_186XXXX Value code 1分钟有效
            stringRedisTemplate.opsForValue().set(phone,code);
            stringRedisTemplate.expire(phone,3, TimeUnit.MINUTES);
            hashMap.put("status", "200");
            hashMap.put("message", "发送成功");
        } catch (Exception e) {
            e.printStackTrace();
            hashMap.put("status", "-200");
            hashMap.put("message", "error");
        }
        return hashMap;
    }

    //3、注册接口
    @RequestMapping("/register")
    public Map register(String code,String phone) {
        HashMap hashMap = new HashMap();
        String codes = stringRedisTemplate.opsForValue().get(phone);
        if (code.equals(codes)){
            hashMap.put("status","200");
            hashMap.put("message","验证码正确");
        }else {
            hashMap.put("status","-200");
            hashMap.put("message","验证码错误");
        }
        return hashMap;
    }

    //4、补充个人信息接口
    /*
    * @RequestBody ：将请求的参数对象（json格式）转换为对象格式
    * */
    @PostMapping("/addUserMessage")
    public Map addUserMessage(@RequestBody User user) {
        HashMap hashMap = new HashMap();
        userService.insert(user);
        hashMap.put("status","200");
        hashMap.put("user",user);
        return hashMap;
    }

    //5、一级页面展示接口
    @RequestMapping("/onePage")
    public Map onePage(String type, String uid, String sub_type) {
        HashMap hashMap = new HashMap();
        try {
            if (type.equals("all")) {
                List<Banner> banners = bannerDao.selectAll();
                List<Album> albums = albumDao.selectAll();
                List<Article> articles = articleDao.selectAll();
                hashMap.put("head", banners);
                hashMap.put("albums", albums);
                hashMap.put("articles", articles);
                hashMap.put("status","200");
            } else if (type.equals("wen")) {
                List<Album> albums = albumDao.selectAll();
                hashMap.put("albums", albums);
                hashMap.put("status", "200");
            } else {
                if (sub_type.equals("ssyj")) {
                    List<Article> articles = articleDao.selectAll();
                    hashMap.put("articles",articles);
                    hashMap.put("status","200");

                }else {
                    List<Article> articles = articleDao.selectAll();
                    hashMap.put("articles",articles);
                    hashMap.put("status","200");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            hashMap.put("status", "-200");
            hashMap.put("message", "errror");
        }
        return hashMap;
    }

    //6、文章详情接口
    @RequestMapping("/articleMess")
    public Map articleMess(String id,String uid){
        HashMap hashMap = new HashMap();
        Article article = new Article();
        article.setId(id);
        articleDao.selectOne(article);
        hashMap.put("status","200");
        hashMap.put("article",article);
        return hashMap;
    }

    //7、专辑详情接口
    @RequestMapping("/albumMess")
    public Map albumMess(String id,String uid){
        HashMap hashMap = new HashMap();
        Album album = new Album();
        album.setId(id);
        albumDao.selectOne(album);
        hashMap.put("status","200");
        hashMap.put("album",album);
        return hashMap;
    }

    //8、展示功课
    @RequestMapping("/showCourse")
    public Map showCourse(Long uid){
        HashMap hashMap = new HashMap();
        Course course = new Course();
        course.setUserId(uid);
        Course course1 = courseDao.selectOne(course);
        hashMap.put("status","200");
        hashMap.put("course",course1);
        return hashMap;
    }

    //9、添加功课
    @RequestMapping("/addCourse")
    public Map addCourse(Long uid,String title){
        HashMap hashMap = new HashMap();
        Course course = new Course();
        course.setTitle(title);
        course.setUserId(uid);
        courseDao.insert(course);
        hashMap.put("status","200");
        hashMap.put("course",course);
        hashMap.put("message","添加成功");
        return hashMap;
    }

    //10、删除功课
    @RequestMapping("/deleteCourse")
    public Map deleteCourse(Long uid,String id){
        HashMap hashMap = new HashMap();
        Course course = new Course();
        course.setId(id);
        course.setUserId(uid);
        courseDao.delete(course);
        hashMap.put("status","200");
        return hashMap;
    }

    //11、展示计数器
    @RequestMapping("/showCounter")
    public Map showCounter(String uid,String id){
        HashMap hashMap = new HashMap();
        Counter counter = new Counter();
        counter.setId(id);
        counter.setUserId(uid);
        Counter counter1 = counterDao.selectOne(counter);
        hashMap.put("status","2000");
        hashMap.put("counter",counter1);
        return hashMap;
    }

    //12、添加计数器
    @RequestMapping("/addCounter")
    public Map addCounter(String uid,String title){
        HashMap hashMap = new HashMap();
        Counter counter = new Counter();
        counter.setTitle(title);
        counter.setUserId(uid);
        counterDao.insert(counter);
        hashMap.put("status","2000");
        hashMap.put("counter",counter);
        return hashMap;
    }

    //13、删除计数器
    @RequestMapping("/deleteCounter")
    public Map deleteCounter(String uid,String id){
        HashMap hashMap = new HashMap();
        Counter counter = new Counter();
        counter.setId(id);
        counter.setUserId(uid);
        counterDao.delete(counter);
        hashMap.put("status","2000");
        return hashMap;
    }

    //14、表更计数器
    @RequestMapping("/changeCounter")
    public Map changeCounter(String uid,String id,Long count){
        HashMap hashMap = new HashMap();
        Counter counter = new Counter();
        counter.setId(id);
        counter.setCount(count);
        counter.setUserId(uid);
        counterDao.updateByPrimaryKeySelective(counter);
        return hashMap;
    }

    //15、修改个人信息
    @RequestMapping("/updateUser")
    public Map updateUser(String password,String photo,String name,String nick_name,String sex,String sign,String location){
        HashMap hashMap = new HashMap();
        User user = new User();
        user.setPassword(password);
        user.setPhoto(photo);
        user.setName(name);
        user.setNickName(nick_name);
        user.setSex(sex);
        user.setSign(sign);
        user.setLocation(location);
        userService.update(user);
        hashMap.put("status","200");
        hashMap.put("user",user);
        return hashMap;
    }

    //16、金刚道友
    @RequestMapping("/friend")
    public Map friend(String id){
        HashMap hashMap = new HashMap();
        List<User> users = userService.quwerySixUser(id);
        hashMap.put("status","200");
        hashMap.put("friends",users);
        return hashMap;
    }

    //17、展示上师列表
    @RequestMapping("/showGuru")
    public Map showGuru(String uid){
        HashMap hashMap = new HashMap();
        List<Guru> gurus = guruDao.selectAll();
        hashMap.put("status","200");
        hashMap.put("gurus",gurus);
        return hashMap;
    }

    //18、添加关注上师
    @RequestMapping("/addGuru")
    public Map addGur(){
        HashMap hashMap = new HashMap();
        return hashMap;
    }
}
