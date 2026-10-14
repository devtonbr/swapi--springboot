package br.com.swapi.api.controller.mapper;


import br.com.swapi.api.constants.Constants;
import br.com.swapi.api.entity.Film;
import br.com.swapi.api.entity.InputParameter;
import br.com.swapi.api.entity.ReturnService;
import br.com.swapi.api.openapi.model.vo.FilmResponse;
import br.com.swapi.api.openapi.model.vo.InputParameterRequest;
import br.com.swapi.api.openapi.model.vo.ReturnServiceResponse;
import br.com.swapi.api.openapi.model.vo.UpdateDescriptionResponse;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface FilmsMapper {

    ReturnServiceResponse map(ReturnService response);

    @Mapping(target = "episodeId", source = "id")
    FilmResponse map(Film response);

    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "detail", constant = Constants.SUCCESS_MESSAGE)
    @Mapping(target = "version", source = "version")
    UpdateDescriptionResponse mapUpdateDescription(Film response);

    InputParameter map(InputParameterRequest request);

}
