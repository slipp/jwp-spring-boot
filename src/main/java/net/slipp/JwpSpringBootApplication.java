package net.slipp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({"net.slipp", "support"})
public class JwpSpringBootApplication {

	public static void main(String[] args) {
		SpringApplication.run(JwpSpringBootApplication.class, args);
	}
}
