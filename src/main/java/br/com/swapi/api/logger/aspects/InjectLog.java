package br.com.swapi.api.logger.aspects;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Atnotação para definir o log com o nivel de log
 * 
 * OBS: esta anotação deve ser utilizada somente em classes BEAN
 * 
 * @author Clayton Morais de Oliveira
 *
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface InjectLog {
	LevelLogger value() default LevelLogger.FATAL;
}