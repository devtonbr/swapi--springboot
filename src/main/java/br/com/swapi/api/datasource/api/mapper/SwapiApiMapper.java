package br.com.swapi.api.datasource.api.mapper;


import br.com.swapi.api.datasource.api.dto.FilmDto;
import br.com.swapi.api.datasource.api.dto.ReturnServiceDto;
import br.com.swapi.api.entity.Film;
import br.com.swapi.api.entity.ReturnService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface SwapiApiMapper {

    SwapiApiMapper INSTANCE = Mappers.getMapper(SwapiApiMapper.class);

    ReturnService map(ReturnServiceDto dto);

    @Mapping(target = "id", source = "episodeId")
    @Mapping(target = "updateDescription", ignore = true)
    // a api não informa versão: o filme é criado na versão inicial
    @Mapping(target = "version", ignore = true)
    Film map(FilmDto dto);

}
