package com.gabsleo.meaudote;

import com.gabsleo.meaudote.entities.AppRole;
import com.gabsleo.meaudote.entities.AppUser;
import com.gabsleo.meaudote.services.AppRoleService;
import com.gabsleo.meaudote.services.AppUserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Date;

@SpringBootApplication
public class MeaudoteApplication {
	@Value("${env.ROOT_NAME}")
	private String ROOT_NAME;
	@Value("${env.ROOT_PASSWORD}")
	private String ROOT_PASSWORD;
	@Value("${env.ROOT_CPF}")
	private String ROOT_CPF;
	@Value("${env.ROOT_EMAIL}")
	private String ROOT_EMAIL;
	@Value("${env.ROOT_PHONE}")
	private String ROOT_PHONE;

	public static void main(String[] args) {
		SpringApplication.run(MeaudoteApplication.class, args);
	}

	@Bean
	CommandLineRunner startup(AppUserService appUserService, AppRoleService appRoleService){
		return args -> {

			appUserService.attachRole(appUserService.findByEmail("gabriel@gmail.com"), appRoleService.save(new AppRole("ADMIN")));
			/*
			appUserService.register(new AppUser(
					ROOT_CPF,
					ROOT_NAME,
					ROOT_PASSWORD,
					ROOT_EMAIL,
					new Date(),
					ROOT_PHONE,
					"pfp.png",
					"banner.png",
					false,
					"Sao Paulo",
					"Franca"
			));*/
		};
	}
}
