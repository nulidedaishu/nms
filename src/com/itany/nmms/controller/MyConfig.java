package com.itany.nmms.controller;

import com.itany.mvc.annotation.Configuration;
import com.itany.mvc.config.ResourceHandlerRegistry;
import com.itany.mvc.config.WebConfigurer;

@Configuration
public class MyConfig extends WebConfigurer {

    @Override
    public void addViewControllers(ResourceHandlerRegistry registry){

        registry.addViewController("backend/login","/showLogin");

        registry.addViewController("backend/main","/showMain");
    }

}
