package com.hahaha.musicshare.common.config;

import com.hahaha.musicshare.common.interceptor.PermitResource;
import com.hahaha.musicshare.common.interceptor.RoleInterceptor;
import com.hahaha.musicshare.common.interceptor.TokenInterceptor;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
@AllArgsConstructor
public class SpringMvcConfig implements WebMvcConfigurer {
    private final TokenInterceptor tokenInterceptor;
    private final PermitResource permitResource;
    private final RoleInterceptor roleInterceptor;

    @Bean
    public CorsFilter corsFilter() {
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        final CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowCredentials(true);
        corsConfiguration.addAllowedHeader("*");
        corsConfiguration.addAllowedOriginPattern("*");
        corsConfiguration.addAllowedMethod("*");
        source.registerCorsConfiguration("/**", corsConfiguration);
        return new CorsFilter(source);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(tokenInterceptor)
                // 添加需要被校验的路径
                .addPathPatterns(permitResource.getValidList(0));
        registry.addInterceptor(roleInterceptor)
                // 添加需要被校验的路径
                .addPathPatterns(permitResource.getValidList(""));
    }
}
