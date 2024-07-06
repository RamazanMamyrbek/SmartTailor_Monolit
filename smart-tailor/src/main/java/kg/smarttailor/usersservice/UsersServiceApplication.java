package kg.smarttailor.usersservice;


import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import org.springframework.context.annotation.Bean;



@EnableConfigurationProperties
@SpringBootApplication
public class UsersServiceApplication {
    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(UsersServiceApplication.class);
        app.run(args);
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
