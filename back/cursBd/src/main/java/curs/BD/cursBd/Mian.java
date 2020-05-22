package curs.BD.cursBd;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories
@SpringBootApplication(scanBasePackages = {"curs.BD.cursBd.repositories", "curs.BD.cursBd.exceptions", "curs.BD.cursBd.security", "curs.BD.cursBd.controllers", "curs.BD.cursBd.dto", "curs.BD.cursBd.users"}, exclude = { SecurityAutoConfiguration.class })
public class Mian {
    public static void main(String[] args) {
       SpringApplication.run(Mian.class, args);
    }
}
