package com.epmserver.gateway.repository;
import com.epmserver.gateway.domain.StateProvinces;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the StateProvinces entity.
 */
@SuppressWarnings("unused")
@Repository
public interface StateProvincesRepository extends JpaRepository<StateProvinces, Long>, JpaSpecificationExecutor<StateProvinces> {

}
