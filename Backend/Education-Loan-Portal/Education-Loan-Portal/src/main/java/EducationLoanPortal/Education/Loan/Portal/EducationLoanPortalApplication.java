package EducationLoanPortal.Education.Loan.Portal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.apache.commons.codec.binary.StringUtils;
@SpringBootApplication
public class EducationLoanPortalApplication {

	public static void main(String[] args) {
		SpringApplication.run(EducationLoanPortalApplication.class, args);
	}

	@Configuration
	@EnableWebMvc
	public class CorsConfig implements WebMvcConfigurer {

		@Override
		public void addCorsMappings(CorsRegistry registry) {
			registry.addMapping("/**")
					.allowedOrigins("*")
					.allowedMethods("*")
					.allowedHeaders("*");
		}
	}
}
