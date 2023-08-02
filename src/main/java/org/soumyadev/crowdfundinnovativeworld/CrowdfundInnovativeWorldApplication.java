package org.soumyadev.crowdfundinnovativeworld;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@OpenAPIDefinition(info = @Info(title = "CrowdFundWorld API", description = "Description of all the CrowdFundWorld API endpoints",contact = @Contact(url = "", name = "crowd fund devTeam", email = "s.mondal8@gmail.com")))
@SpringBootApplication
@Configuration
public class CrowdfundInnovativeWorldApplication {

	public static void main(String[] args) {
		SpringApplication.run(CrowdfundInnovativeWorldApplication.class, args);
	}
	@Bean
	CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();
		configuration.setAllowedOrigins(Arrays.asList("*"));
		configuration.setAllowedMethods(Arrays.asList("*"));
		configuration.setAllowedHeaders(Arrays.asList("*"));
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		return source;
	}

}
