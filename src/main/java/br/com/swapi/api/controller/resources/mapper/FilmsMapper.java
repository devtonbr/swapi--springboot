package br.com.swapi.api.controller.resources.mapper;


import br.com.swapi.api.model.vo.Film;
import br.com.swapi.api.model.vo.InputParameter;
import br.com.swapi.api.model.vo.ObjectRsp;
import br.com.swapi.api.model.vo.ReturnService;
import br.com.swapi.api.openapi.model.vo.FilmResponse;
import br.com.swapi.api.openapi.model.vo.InputParameterRequest;
import br.com.swapi.api.openapi.model.vo.ObjectResponse;
import br.com.swapi.api.openapi.model.vo.ReturnServiceResponse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface FilmsMapper {

    FilmsMapper INSTANCE = Mappers.getMapper(FilmsMapper.class);

    ReturnServiceResponse map(ReturnService response);

    FilmResponse map(Film response);

    ObjectResponse map(ObjectRsp response);

    InputParameter map(InputParameterRequest request);

}
