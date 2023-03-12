package br.com.swapi.api.controller.resources;

import javax.servlet.http.HttpServletRequest;

import br.com.swapi.api.config.properties.SwApiProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.swapi.api.model.bean.FilmBean;
import br.com.swapi.api.model.vo.ObjectResponse;
import br.com.swapi.api.model.vo.Film;
import br.com.swapi.api.model.vo.InputParameter;
import br.com.swapi.api.model.vo.ReturnService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * Bean de AutenticaÃ§Ã£o
 * 
 * @author Clayton Morais de Oliveira
 *
 */
@RestController
@RequestMapping(value="/api")
public class FilmResource  {



    @Autowired
    private transient FilmBean filmsBean;
    
    @Autowired
	private HttpServletRequest request;

    /**
     * Gera uma Resposta da lista de Filmes
     *
     * @return Lista de Filmes
     */
    @ApiOperation(value = "Efetua a listagens dos Filmes",responseReference="ReturnService")
    @ApiResponses(value = {
    		@ApiResponse(code = 200, message = "Sucesso"), 
    		@ApiResponse(code = 500, message = "Erro no Banco de dados", response = ObjectResponse.class),
    		@ApiResponse(code = 417, message = "Erros de negÃ³cio", response = ObjectResponse.class)
    })    
    @RequestMapping(
    		value = "/films", 
    		method = RequestMethod.GET, 
    		produces = MediaType.APPLICATION_JSON_VALUE
    		)
    public ResponseEntity<ReturnService> listAll() {
        return new ResponseEntity<ReturnService>(filmsBean.listAll(request),HttpStatus.OK);
    } 
    
    /**
     * Gera uma Resposta detalhe do Filme informado atravÃ©s do seu identificador
     *
     * @return Lista de Filmes
     */
    @ApiOperation(value = "Detalha o Filme pelo identificador ",responseReference="Film")
    @ApiResponses(value = {
    		@ApiResponse(code = 200, message = "Sucesso"), 
    		@ApiResponse(code = 500, message = "Erro no Banco de dados", response = ObjectResponse.class),
    		@ApiResponse(code = 417, message = "Não encontrado", response = ObjectResponse.class)
    })    
    @RequestMapping(
    		value = "/films/{id}", 
    		method = RequestMethod.GET, 
    		produces = MediaType.APPLICATION_JSON_VALUE
    		)
    public ResponseEntity<Film> findById(@PathVariable("id") Integer id) {
        return new ResponseEntity<Film>(filmsBean.findById(request,id),HttpStatus.OK);
    } 
    
    /**
     * Modifica a descrição, atualizando a versão. através do seu identificador
     *
     * @return Lista de Filmes
     */
    @ApiOperation(value = "Modifica a descrição, atualizando a versão.",responseReference="Film")
    @ApiResponses(value = {
    		@ApiResponse(code = 200, message = "Sucesso"), 
    		@ApiResponse(code = 500, message = "Erro no Banco de dados", response = ObjectResponse.class),
    		@ApiResponse(code = 404, message = "Não encontrado", response = ObjectResponse.class)
    })    
    @RequestMapping(
    		value = "/films/{id}", 
    		method = RequestMethod.PUT, 
    		produces = MediaType.APPLICATION_JSON_VALUE
    		)
    public ResponseEntity<ObjectResponse> updateDescription(@PathVariable("id") Integer id, @RequestBody InputParameter inputParameter) {
        return new ResponseEntity<ObjectResponse>(filmsBean.updateDescription(request,id,inputParameter),HttpStatus.OK);
    }
    
    
}
