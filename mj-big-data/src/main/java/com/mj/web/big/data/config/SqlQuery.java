package com.mj.web.big.data.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.Map;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "sql")
public class SqlQuery {
    private Map<String, String> listTables;
    private Map<String, String> listColumns;
}
