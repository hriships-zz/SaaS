package com.appdirect;

import com.appdirect.common.services.OAuthHelper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.web.client.RestTemplate;

/**
 * Springboot application
 */
@SpringBootApplication
public class SaasAppApplication {

	@Bean
	@Scope("prototype")
	public OAuthHelper oAuthHelper() {
		return new OAuthHelper();
	}

	@Bean
	@Scope("prototype")
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}


	public static void main(String[] args) {
		SpringApplication.run(SaasAppApplication.class, args);
	}
}
