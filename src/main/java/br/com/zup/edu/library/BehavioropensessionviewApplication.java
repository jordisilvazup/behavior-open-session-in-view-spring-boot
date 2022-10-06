package br.com.zup.edu.library;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;

@SpringBootApplication
@EnableAutoConfiguration(exclude = ErrorMvcAutoConfiguration.class) // Needed by Zalando Problem lib
public class BehaviorOpenSessionViewApplication {

	public static void main(String[] args) {
		SpringApplication.run(BehaviorOpenSessionViewApplication.class, args);
	}

}
