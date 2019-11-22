package com.epmserver.gateway.service.mapper;

import com.epmserver.gateway.domain.*;
import com.epmserver.gateway.service.dto.UnitMeasureDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link UnitMeasure} and its DTO {@link UnitMeasureDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface UnitMeasureMapper extends EntityMapper<UnitMeasureDTO, UnitMeasure> {



    default UnitMeasure fromId(Long id) {
        if (id == null) {
            return null;
        }
        UnitMeasure unitMeasure = new UnitMeasure();
        unitMeasure.setId(id);
        return unitMeasure;
    }
}
