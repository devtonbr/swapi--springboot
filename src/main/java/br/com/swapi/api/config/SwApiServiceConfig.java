package br.com.swapi.api.config;


import br.com.swapi.api.exception.FilmException;
import br.com.swapi.api.usecase.SwapiUseCase;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClientRequestException;

@Component
public class SwApiServiceConfig implements SmartInitializingSingleton {


    private static final Logger LOGGER = LogManager.getLogger(SwApiServiceConfig.class);

    @Autowired
    private SwapiUseCase swapiUseCase;

    /**
     * Carrega os filmes na subida da aplicação, antes de aceitar requisições
     */
    @Override
    public void afterSingletonsInstantiated() {
        try {
            LOGGER.info("Carregando os filmes na subida da aplicação");
            swapiUseCase.loadAll();
        } catch (FilmException e) {
            throw new IllegalStateException("Erro ao listar os filmes[".concat(e.getHttpStatus().toString()).concat("]"),e);
        } catch (WebClientRequestException e) {
            throw new IllegalStateException("Erro ao conectar na api de filmes[".concat(e.getUri().toString()).concat("]"),e);
        }
    }
}
