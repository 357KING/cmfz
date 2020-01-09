package com.baizhi.cjw.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class ToMyBean implements ApplicationContextAware {

    private static ApplicationContext applicationContext;
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext  =  applicationContext;
    }
    //case1
    public static Object getByName(String name){
        Object bean = applicationContext.getBean(name);
        return bean;
    }
    //case2
    public static Object getByClazz(Class clazz){
        Object bean = applicationContext.getBean(clazz);
        return bean;
    }
}
