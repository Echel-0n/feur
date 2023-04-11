package projet.jee;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
@ServletComponentScan
public class JeeApplication {
	public static void main(String[] args) {
		SpringApplication.run(JeeApplication.class, args);
	}


}