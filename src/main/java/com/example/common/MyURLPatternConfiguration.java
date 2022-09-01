//package com.example.common;
//
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
//
//public class MyURLPatternConfiguration {
//    /**
//     * 设置静态资源映射
//     * @param registry
//     */
//    @Override
//    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
//        log.info("开始进行静态资源映射...");
//        registry.addResourceHandler("/backend/**").addResourceLocations("classpath:/backend/");
//        registry.addResourceHandler("/front/**").addResourceLocations("classpath:/front/");
//        log.info("");
//    }
//}