package br.com.swapi.api.controller;

import br.com.swapi.api.controller.mapper.FilmsMapper;
import br.com.swapi.api.usecase.FilmUseCase;
import br.com.swapi.api.openapi.model.vo.FilmResponse;
import br.com.swapi.api.openapi.model.vo.InputParameterRequest;
import br.com.swapi.api.openapi.model.vo.UpdateDescriptionResponse;
import br.com.swapi.api.openapi.model.vo.ReturnServiceResponse;
import br.com.swapi.api.openapi.resources.FilmsApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value="/")
public class FilmsApiImpl implements FilmsApi {

    @Autowired
    private transient FilmUseCase filmsUseCase;

    @Override
    public ResponseEntity<FilmResponse> findById(Integer id) {
        return new ResponseEntity<FilmResponse>(FilmsMapper.INSTANCE.map(filmsUseCase.findById(id)),HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ReturnServiceResponse> listAllFilms() {
        return new ResponseEntity<ReturnServiceResponse>(FilmsMapper.INSTANCE.map(filmsUseCase.listAll()), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<UpdateDescriptionResponse> updateDescription(Integer id, InputParameterRequest inputRequest) {
        return new ResponseEntity<UpdateDescriptionResponse>(FilmsMapper.INSTANCE.mapUpdateDescription(filmsUseCase.updateDescription(id,FilmsMapper.INSTANCE.map(inputRequest))),HttpStatus.OK);
    }
}
