package com.project.toy;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.core.env.Environment;

import java.util.Arrays;

@SpringBootApplication
@ComponentScan(basePackages = "com.project")
//@Import( AppConfig.class )
@RequiredArgsConstructor
public class ToyApplication {

	private final Environment env;

	public static void main(String[] args) {
		SpringApplication.run(ToyApplication.class, args);
	}

	@Bean
	public ApplicationRunner applicationRunner() {
		return args -> {
			System.out.println("profile : " + Arrays.toString(env.getActiveProfiles()));
		};
	}

}
