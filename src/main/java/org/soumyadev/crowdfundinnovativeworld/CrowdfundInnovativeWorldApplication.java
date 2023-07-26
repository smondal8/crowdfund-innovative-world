package org.soumyadev.crowdfundinnovativeworld;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@OpenAPIDefinition(info = @Info(title = "Failsafe Logbook API", description = "Description of all the Failsafelogbook API endpoints",contact = @Contact(url = "", name = "crowd fund devTeam", email = "s.mondal8@gmail.com")))
@SpringBootApplication
public class CrowdfundInnovativeWorldApplication {

	public static void main(String[] args) {
		SpringApplication.run(CrowdfundInnovativeWorldApplication.class, args);
	}

}
