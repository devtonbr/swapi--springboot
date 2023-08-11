package br.com.swapi.api.config;


import br.com.swapi.api.Application;
import br.com.swapi.api.model.exception.FilmException;
import br.com.swapi.api.model.starwars.service.FilmService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.ManagedBean;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import static br.com.swapi.api.model.constants.Constants.FILMS_ATTRIBUTE;

@ManagedBean
public class SwApiServiceConfig implements ServletContextInitializer {


    private static final Logger LOGGER = LogManager.getLogger(SwApiServiceConfig.class);

    @Autowired
    private FilmService filmService;

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        try {
            LOGGER.info("Executando método onstartup");
            servletContext.setAttribute(FILMS_ATTRIBUTE, filmService.listAllFilmsByService());
        } catch (FilmException e) {
            throw new RuntimeException("Erro ao listar os filmes[".concat(e.getHttpStatus().toString()).concat("]"),e);
        }
    }
}
