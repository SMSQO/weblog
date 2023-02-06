package com.weblog.framework.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan("com.weblog.persistence.mapper")
public class MyBatisConfig {
}
