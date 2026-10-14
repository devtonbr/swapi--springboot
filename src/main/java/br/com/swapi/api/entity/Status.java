package br.com.swapi.api.entity;

import java.io.Serializable;

/**
 * Classe para Tratar os Retornos de Negocio
 *
 * @author Clayton Morais de Oliveira
 *
 * @param detail Mensagem
 */
public record Status(String detail) implements Serializable {
}
