package com.ztkj.platform.update;

import com.ztkj.platform.update.Utils.CatchUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.util.unit.DataSize;

import javax.servlet.MultipartConfigElement;


@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class})
@ComponentScan(basePackages = {"com.ztkj.*"})
public class PlatformUpdateApplication {
	public static void main(String[] args) {
		SpringApplication.run(PlatformUpdateApplication.class, args);
		CatchUtils.register();
	}
	//设置上传的最大大小
	@Bean
	public MultipartConfigElement multipartConfigElement() {
		MultipartConfigFactory factory = new MultipartConfigFactory();
		factory.setMaxFileSize(DataSize.parse("120MB"));
		factory.setMaxRequestSize(DataSize.parse("120MB"));
		return factory.createMultipartConfig();
	}
}
