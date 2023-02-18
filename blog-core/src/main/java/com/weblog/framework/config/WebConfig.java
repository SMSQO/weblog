package com.weblog.framework.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    public final static String AVATAR_URL_PATTERN = "/blogger/*/avatar";
    public final static String AVATAR_REAL_PATH = "file:./src/avatar/";

    // TODO NOT FINISHED
    public final static String ATTACHMENT_URL_PATTERN = "";
    public final static String ATTACHMENT_REAL_PATH = "./src/files/"; // TODO

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(0, new MappingJackson2HttpMessageConverter());
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOriginPatterns("*")
                        .allowedMethods("*")
                        .allowedHeaders("*")
                        .allowCredentials(true)
                        .exposedHeaders(HttpHeaders.SET_COOKIE).maxAge(3600L);
            }
        };
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(AVATAR_URL_PATTERN)
                .addResourceLocations(AVATAR_REAL_PATH);

        // TODO
        registry.addResourceHandler(ATTACHMENT_URL_PATTERN)
                .addResourceLocations(ATTACHMENT_REAL_PATH);
    }
}
