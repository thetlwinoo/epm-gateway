package com.epmserver.gateway.service.mapper;

import com.epmserver.gateway.domain.*;
import com.epmserver.gateway.service.dto.MaterialsDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Materials} and its DTO {@link MaterialsDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface MaterialsMapper extends EntityMapper<MaterialsDTO, Materials> {



    default Materials fromId(Long id) {
        if (id == null) {
            return null;
        }
        Materials materials = new Materials();
        materials.setId(id);
        return materials;
    }
}
