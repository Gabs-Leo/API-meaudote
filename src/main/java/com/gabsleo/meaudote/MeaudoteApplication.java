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
/*
	@Bean
	CommandLineRunner startup(AppUserService appUserService, AppRoleService appRoleService){
		return args -> {
			try{
				appRoleService.findByName("USER");
			} catch (Exception e){
				appRoleService.save(new AppRole("USER"));
			}
			try{
				appRoleService.findByName("ADMIN");
			} catch (Exception e){
				appRoleService.save(new AppRole("ADMIN"));
			}
			try{
				appUserService.findByEmail(ROOT_EMAIL);
			} catch (Exception e){
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
				));
				appUserService.attachRole(appUserService.findByEmail(ROOT_EMAIL), appRoleService.findByName("ADMIN"));
			}
		};
	}*/
}
