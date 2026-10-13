package br.com.swapi.api;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


/**
 * Classe de Classe inicial do Springboot
 * 
 * @author Clayton Morais de Oliveira
 *
 */
@SpringBootApplication
public class Application {

	private static final Logger LOGGER = LogManager.getLogger(Application.class);
	
    /**
     * @param args
     */
    public static void main(String[] args) {
    	LOGGER.info("Iniciando o a Aplicação - SWAPI");
        SpringApplication.run(Application.class, args);
    }


}
