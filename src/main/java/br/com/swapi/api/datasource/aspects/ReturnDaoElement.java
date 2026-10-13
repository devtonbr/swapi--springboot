package br.com.swapi.api.datasource.aspects;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import br.com.swapi.api.constants.Constants;
import br.com.swapi.api.exception.DaoException;

/**
 * Aspecto para tratar o retorno do Dao
 * 
 * @author Clayton Morais de Oliveira
 *
 */
@Aspect
@Component
public class ReturnDaoElement implements Constants { 

	/**
	 * Captura o codigo de Erro vindo da Procedure e lança uma exceção de negocio
	 * 
	 * @param joinPoint Método a ser executado
	 * @return Objeto de Retorno em caso de sucesso
	 * @throws Throwable 
	 */
	@Around("@annotation(ReturnNullObject)")	
	public Object getNullReturn(ProceedingJoinPoint joinPoint) throws Throwable {
	    Object proceed = joinPoint.proceed();	 
	    if (proceed == null){
	    	throw new DaoException(NOT_FOUND_MESSAGE);
	    }  
	    return proceed;
	}	

}
