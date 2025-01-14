package com.Cr_8;

import java.io.File;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class Cr8Application {

	public static void main(String[] args) {
		 String externalConfigPath = "./user-config/external-config.properties";

	        // Controlla se il file esiste
	        File externalConfigFile = new File(externalConfigPath);
	        if (externalConfigFile.exists()) {
	            System.out.println("Caricando configurazioni da: " + externalConfigPath);

	            // Configura l'applicazione per leggere il file esterno
	            new SpringApplicationBuilder(Cr8Application.class)
	                    .properties("spring.config.location=" + externalConfigPath)
	                    .run(args);
	        } else {
	            System.out.println("File esterno non trovato. Caricamento configurazioni predefinite.");
	            SpringApplication.run(Cr8Application.class, args);
	        }
	}

}
