package curs.BD.cursBd.testMessage;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories
@SpringBootApplication(scanBasePackages = {"curs.BD.cursBd"}, exclude = { SecurityAutoConfiguration.class })
public class Mian {
    public static void main(String[] args) {
       SpringApplication.run(Mian.class, args);
    }
}
