package br.com.swapi.api;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import br.com.swapi.api.config.properties.SwApiProperties;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import br.com.swapi.api.model.constants.Constants;
import br.com.swapi.api.model.exception.FilmException;
import br.com.swapi.api.model.starwars.service.FilmService;


/**
 * Classe de Classe inicial do Springboot
 * 
 * @author Clayton Morais de Oliveira
 *
 */
@SpringBootApplication
@ComponentScan
public class Application implements Constants {

	private static final Logger LOGGER = LogManager.getLogger(Application.class);
	
    /**
     * @param args
     */
    public static void main(String[] args) {
    	LOGGER.info("Iniciando o a Aplicação - SWAPI");
        SpringApplication.run(Application.class, args);
    }


}
