package br.com.swapi.api.test;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

/**
 * Classe Abstrata de Teste para carregar informações do Springboot
 *  
 * @author Clayton Morais de Oliveira
 *
 */
@SpringBootTest()
@ActiveProfiles("dev")
public abstract class AbstractTest {}
