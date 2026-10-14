package br.com.swapi.api.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import br.com.swapi.api.exception.MemoryStoreException;
import br.com.swapi.api.entity.Status;

@ControllerAdvice
public class ExceptionHandler extends ResponseEntityExceptionHandler {

	private static final Logger LOGGER = LogManager.getLogger(ExceptionHandler.class);
	
    /**
     * Retorna as mensagens de erro inesperados
     * 
     * @param exception exception de erro
     * @param request Objeto WebRequest
     * @return Objeto de Retorno
     */	
	@org.springframework.web.bind.annotation.ExceptionHandler(Exception.class)
    public final ResponseEntity<Status> handleAllExceptions(Exception exception, WebRequest request) {
		 LOGGER.error("Erro ao executar a api: "+exception.getMessage(), exception);
	     return new ResponseEntity<Status>(new Status("Erro interno no Servidor"), HttpStatus.INTERNAL_SERVER_ERROR);
    }
 
    /**
     *' Retorna as mensagens de erro do dao
     * 
     * @param exception exception de negocio
     * @param request Objeto WebRequest
     * @return Objeto de Retorno
     */
    @org.springframework.web.bind.annotation.ExceptionHandler(MemoryStoreException.class)
    public final ResponseEntity<Status> handleNullDaoException(MemoryStoreException exception, WebRequest request) {
    	Status error = new Status(exception.getMessage());
    	LOGGER.error(exception.getMessage());
        return new ResponseEntity<Status>(error,HttpStatus.NOT_FOUND);
    }
	
}
