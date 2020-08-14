package com.ztkj.platform.update.config;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
/**
 * @Author: zhang-jun
 * @Date: 2020/8/14 17:22
 * @Description:
 */
@Component
@ConfigurationProperties(prefix = "file")
@PropertySource(value = "classpath:FilePath.properties")
@Data
public class FileProp {
    String linuxbasepath;
    String winbasepath;
}
