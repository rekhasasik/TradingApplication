package com.trade;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Configuration;

@SpringBootApplication
@Configuration
public class TradingApp extends SpringBootServletInitializer {
	public static void main(String[] args) {
		new SpringApplicationBuilder(TradingApp.class)
		.sources(TradingApp.class)
		.run(args);
	}
	
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(TradingApp.class);
	}

}
