package com.mj.web.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class ConfigDebug {

    @Autowired
    private Environment env;

    @PostConstruct
    public void printProperties() {
        System.out.println("Active Profiles: " + String.join(", ", env.getActiveProfiles()));
    }
}
