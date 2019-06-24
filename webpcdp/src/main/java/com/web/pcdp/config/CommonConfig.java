package com.web.pcdp.config;

import javax.servlet.MultipartConfigElement;

import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CommonConfig {

	/**
	 * 设置上传文件大小
	 *
	 * @return
	 */
	@Bean
	public MultipartConfigElement multipartConfigElement() {

		MultipartConfigFactory factory = new MultipartConfigFactory();
		//factory.setMaxFileSize( 1024L * 1024L);
		System.out.println(">>>max file size change : 5m.");
		return factory.createMultipartConfig();
	}
}