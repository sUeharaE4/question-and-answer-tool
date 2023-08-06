package com.qa.tool_manager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
@ComponentScan({"com.qa.tool_manager", "com.qa.common_libs"})
public class ToolManagerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ToolManagerApplication.class, args);
	}
}
