package com.epmserver.gateway.service.mapper;

import com.epmserver.gateway.domain.*;
import com.epmserver.gateway.service.dto.StateProvincesDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link StateProvinces} and its DTO {@link StateProvincesDTO}.
 */
@Mapper(componentModel = "spring", uses = {CountriesMapper.class})
public interface StateProvincesMapper extends EntityMapper<StateProvincesDTO, StateProvinces> {

    @Mapping(source = "country.id", target = "countryId")
    @Mapping(source = "country.name", target = "countryName")
    StateProvincesDTO toDto(StateProvinces stateProvinces);

    @Mapping(source = "countryId", target = "country")
    StateProvinces toEntity(StateProvincesDTO stateProvincesDTO);

    default StateProvinces fromId(Long id) {
        if (id == null) {
            return null;
        }
        StateProvinces stateProvinces = new StateProvinces();
        stateProvinces.setId(id);
        return stateProvinces;
    }
}
