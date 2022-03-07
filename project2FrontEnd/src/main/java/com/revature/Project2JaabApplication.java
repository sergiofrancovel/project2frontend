package com.revature;

import com.revature.model.Patient;
import com.revature.model.Role;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Project2JaabApplication {

    public static void main(String[] args) {
        SpringApplication.run(Project2JaabApplication.class, args);
    }

    @Bean
    public CommandLineRunner runner(){
        return args -> {
//            Patient patient = new Patient("John", "Shepard", "commandershepard@systemalliance.org", "NormandySR2",
//                    "855-264-1147", "AB-", "metal implants", Role.PATIENT);
        };
    }

}
