package br.com.swapi.api.entity;

import java.io.Serializable;

/**
 * Parametros de alteração do filme
 *
 * @param description Nova descrição do filme
 */
public record InputParameter(String description) implements Serializable {
}
