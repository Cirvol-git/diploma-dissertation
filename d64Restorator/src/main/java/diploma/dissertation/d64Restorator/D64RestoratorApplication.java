package diploma.dissertation.d64Restorator;

import diploma.dissertation.d64Restorator.service.Singleton;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class D64RestoratorApplication {

	@Bean
	public Singleton singleton() {
		return new Singleton();
	}

	public static void main(String[] args) {
		SpringApplication.run(D64RestoratorApplication.class, args);
	}

}
