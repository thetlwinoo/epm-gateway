package com.epmserver.gateway.service.mapper;

import com.epmserver.gateway.domain.*;
import com.epmserver.gateway.service.dto.CountriesDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Countries} and its DTO {@link CountriesDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CountriesMapper extends EntityMapper<CountriesDTO, Countries> {



    default Countries fromId(Long id) {
        if (id == null) {
            return null;
        }
        Countries countries = new Countries();
        countries.setId(id);
        return countries;
    }
}
