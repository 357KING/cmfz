package com.baizhi.cjw.controller;

import com.alibaba.druid.support.json.JSONUtils;
import io.goeasy.GoEasy;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("chat")
public class ChatController {

    @RequestMapping("chat")
    public Map chat(String message){
        HashMap hashMap = new HashMap();
        GoEasy goEasy = new GoEasy( "http://rest-hangzhou.goeasy.io", "BC-55c2cbbdd73e4c49940147d6a9c6ddc5");
        String ss = JSONUtils.toJSONString(message);
        goEasy.publish("cmfz", ss);
        hashMap.put("cmfz",ss);
        return hashMap;
    }
}
