package net.slipp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@ComponentScan({ "net.slipp", "support" })
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class JwpSpringBootApplication {

    public static void main(String[] args) {
        SpringApplication.run(JwpSpringBootApplication.class, args);
    }
}
