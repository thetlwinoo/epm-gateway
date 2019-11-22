package com.epmserver.gateway.service.mapper;

import com.epmserver.gateway.domain.*;
import com.epmserver.gateway.service.dto.VehicleTemperaturesDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link VehicleTemperatures} and its DTO {@link VehicleTemperaturesDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface VehicleTemperaturesMapper extends EntityMapper<VehicleTemperaturesDTO, VehicleTemperatures> {



    default VehicleTemperatures fromId(Long id) {
        if (id == null) {
            return null;
        }
        VehicleTemperatures vehicleTemperatures = new VehicleTemperatures();
        vehicleTemperatures.setId(id);
        return vehicleTemperatures;
    }
}
