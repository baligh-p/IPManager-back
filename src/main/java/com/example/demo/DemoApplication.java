package com.example.demo;


import com.example.demo.Model.AppUser;
import com.example.demo.Service.AppUserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}
	@Bean
	CommandLineRunner run(AppUserService appUserService)
	{
		return args-> {
			appUserService.saveUser(new AppUser(1 , "Baligh09" , "21912883Beligh" , "Baligh Zoghlami",null,"ROLE_ADMIN"));
		};
	}
	@Bean
	PasswordEncoder passwordEncoder(){
		return new BCryptPasswordEncoder();
	}
}
