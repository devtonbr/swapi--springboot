package br.com.swapi.api.config;


import br.com.swapi.api.config.properties.SwApiProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(SwApiProperties.class)
public class SwApiConfigProperties {
}
