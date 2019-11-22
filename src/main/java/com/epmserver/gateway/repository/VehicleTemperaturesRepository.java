package com.epmserver.gateway.repository;
import com.epmserver.gateway.domain.VehicleTemperatures;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the VehicleTemperatures entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VehicleTemperaturesRepository extends JpaRepository<VehicleTemperatures, Long>, JpaSpecificationExecutor<VehicleTemperatures> {

}
