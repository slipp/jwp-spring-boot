package net.slipp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@ComponentScan({"net.slipp", "support"})
@EnableJpaAuditing
@EnableAspectJAutoProxy(proxyTargetClass=true)
public class JwpSpringBootApplication {
	public static void main(String[] args) {
		SpringApplication.run(JwpSpringBootApplication.class, args);
	}
}
