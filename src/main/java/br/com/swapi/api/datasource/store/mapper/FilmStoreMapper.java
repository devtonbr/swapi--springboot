package br.com.swapi.api.datasource.store.mapper;


import br.com.swapi.api.datasource.store.dto.FilmStoreDto;
import br.com.swapi.api.datasource.store.dto.ReturnServiceStoreDto;
import br.com.swapi.api.entity.Film;
import br.com.swapi.api.entity.ReturnService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface FilmStoreMapper {

    ReturnServiceStoreDto map(ReturnService films);

    FilmStoreDto map(Film film);

    ReturnService map(ReturnServiceStoreDto dto);

    @Mapping(target = "updateDescription", ignore = true)
    Film map(FilmStoreDto dto);

}
