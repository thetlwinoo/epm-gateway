package com.epmserver.gateway.service.mapper;

import com.epmserver.gateway.domain.*;
import com.epmserver.gateway.service.dto.CultureDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Culture} and its DTO {@link CultureDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CultureMapper extends EntityMapper<CultureDTO, Culture> {



    default Culture fromId(Long id) {
        if (id == null) {
            return null;
        }
        Culture culture = new Culture();
        culture.setId(id);
        return culture;
    }
}
