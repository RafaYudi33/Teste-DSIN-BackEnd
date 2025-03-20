package org.rafs.tstedsin.Config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Value("${cors.originPatterns:}")
    private String corsMap = "";

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        var allowedOrigins = corsMap.split(",");
        registry
                .addMapping("/**")
                .allowedMethods("*")
                .allowedOrigins(allowedOrigins)
                .allowedHeaders("*")
                .allowCredentials(true);
    }
}
