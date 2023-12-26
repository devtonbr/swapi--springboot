package br.com.swapi.api.controller.resources.impl;

import br.com.swapi.api.controller.resources.mapper.FilmsMapper;
import br.com.swapi.api.model.bean.FilmBean;
import br.com.swapi.api.openapi.model.vo.FilmResponse;
import br.com.swapi.api.openapi.model.vo.InputParameterRequest;
import br.com.swapi.api.openapi.model.vo.ObjectResponse;
import br.com.swapi.api.openapi.model.vo.ReturnServiceResponse;
import br.com.swapi.api.openapi.resources.FilmsApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;


@RestController
@RequestMapping(value="/")
public class FilmsApiImpl implements FilmsApi {

    @Autowired
    private transient FilmBean filmsBean;

    @Autowired
    private HttpServletRequest request;

    @Override
    public ResponseEntity<FilmResponse> findById(Integer id) {
        return new ResponseEntity<FilmResponse>(FilmsMapper.INSTANCE.map(filmsBean.findById(request,id)),HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ReturnServiceResponse> listAllFilms() {
        return new ResponseEntity<ReturnServiceResponse>(FilmsMapper.INSTANCE.map(filmsBean.listAll(request)), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ObjectResponse> updateDescription(Integer id, InputParameterRequest inputRequest) {
        return new ResponseEntity<ObjectResponse>(FilmsMapper.INSTANCE.map(filmsBean.updateDescription(request,id,FilmsMapper.INSTANCE.map(inputRequest))),HttpStatus.OK);
    }
}
