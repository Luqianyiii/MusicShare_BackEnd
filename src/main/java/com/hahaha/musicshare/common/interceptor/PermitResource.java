package com.hahaha.musicshare.common.interceptor;

import lombok.SneakyThrows;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

//TODO 拦截路径尚未配置
@Component
public class PermitResource {
    /**
     * 指定被 过滤器 检查的URL
     */
    @SneakyThrows
    public List<String> getValidList(int a) {
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        Resource[] resources = resolver.getResources("classpath*:application.yml");
        String key = "auth.login_urls";
        return getPropertiesList(key, resources);
    }
    @SneakyThrows
    public List<String> getValidList(String a) {
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        Resource[] resources = resolver.getResources("classpath*:application.yml");
        String key = "auth.valid_urls";
        return getPropertiesList(key, resources);
    }

    private List<String> getPropertiesList(String key, Resource... resources) {
        List<String> list = new ArrayList<>();
        // 解析资源⽂件
        for (Resource resource : resources) {
            Properties properties = loadYamlProperties(resource);
            for (Map.Entry<Object, Object> entry : properties.entrySet()) {
                String tmpKey = StringUtils.substringBefore(entry.getKey()
                        .toString(), "[");
                if (tmpKey.equalsIgnoreCase(key)) {
                    list.add(entry.getValue().toString());
                }
            }
        }
        return list;
    }

    private Properties loadYamlProperties(Resource... resources) {
        YamlPropertiesFactoryBean factory = new YamlPropertiesFactoryBean();
        factory.setResources(resources);
        factory.afterPropertiesSet();
        return factory.getObject();
    }
}