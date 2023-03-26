package com.smartmug.device.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

@SpringBootApplication
public class DeviceConfigurationApplication {

	private static final Logger logger = LoggerFactory.getLogger(DeviceConfigurationApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(DeviceConfigurationApplication.class, args);
	}

	@Bean
	protected ServletContextListener listener() {
		return new ServletContextListener() {

			public void contextInitialized(ServletContextEvent sce) {
				logger.info("ServletContext initialized");
			}

			public void contextDestroyed(ServletContextEvent sce) {
				logger.info("ServletContext destroyed");
			}

		};
	}

//	@Bean
//	public CacheControlHandlerInterceptor cacheControlHandlerInterceptor() {
//		return new CacheControlHandlerInterceptor();
//	}

}
