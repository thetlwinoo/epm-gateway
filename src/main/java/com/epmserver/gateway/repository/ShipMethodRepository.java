package com.epmserver.gateway.repository;
import com.epmserver.gateway.domain.ShipMethod;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ShipMethod entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ShipMethodRepository extends JpaRepository<ShipMethod, Long>, JpaSpecificationExecutor<ShipMethod> {

}
