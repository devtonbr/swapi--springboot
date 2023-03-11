package br.com.swapi.api.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import br.com.swapi.api.model.exception.DaoException;
import br.com.swapi.api.model.vo.ObjectResponse;
import br.com.swapi.api.model.vo.statics.InternalServerError;

@ControllerAdvice
public class ReturnDaoExceptionHandler extends ResponseEntityExceptionHandler {

	private static final Logger LOGGER = LogManager.getLogger(ReturnDaoExceptionHandler.class);
	
    /**
     * Retorna as mensagens de erro inesperados
     * 
     * @param exception exception de erro
     * @param request Objeto WebRequest
     * @return Objeto de Retorno
     */	
	@ExceptionHandler(Exception.class)
    public final ResponseEntity<ObjectResponse> handleAllExceptions(Exception exception, WebRequest request) {
		 LOGGER.error("Erro ao executar a api: "+exception.getMessage(), exception);
	     return new ResponseEntity<ObjectResponse>(new InternalServerError(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
 
    /**
     *' Retorna as mensagens de erro do dao
     * 
     * @param exception exception de negocio
     * @param request Objeto WebRequest
     * @return Objeto de Retorno
     */
    @ExceptionHandler(DaoException.class)
    public final ResponseEntity<ObjectResponse> handleNullDaoException(DaoException exception, WebRequest request) {
    	ObjectResponse error = new ObjectResponse(exception.getMessage());
    	LOGGER.error(exception.getMessage());
        return new ResponseEntity<ObjectResponse>(error,HttpStatus.NOT_FOUND);
    }
	
}
