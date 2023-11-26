package com.jack.LabSystem.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class MyCorsConfig {

    @Bean
    public CorsFilter corsFilter() {
        CorsConfiguration configuration = new CorsConfiguration();

        // 允许什么网址来异步访问
        configuration.addAllowedOrigin("http://localhost:8888");

        // 获取cookie
        configuration.setAllowCredentials(true);

        // 允许什么方法？ POST、GET，此处为* 意味全部允许
        configuration.addAllowedMethod("*");

        // 允许所有的请求头
        configuration.addAllowedHeader("*");

        // 设置资源过滤器，过滤什么资源
        UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
        urlBasedCorsConfigurationSource.registerCorsConfiguration("/**",configuration);

        return new CorsFilter(urlBasedCorsConfigurationSource);
    }
}
