package com.shopit.shopit.global.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.nio.file.Paths;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "file.image")
public class FileProperties {

    private String root;
    private String productOption;

    public String resolvePath(String subDir) {
        return Paths.get(root, subDir).toString();
    }
}