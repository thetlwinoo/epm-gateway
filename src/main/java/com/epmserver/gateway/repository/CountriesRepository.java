package com.epmserver.gateway.repository;
import com.epmserver.gateway.domain.Countries;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Countries entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CountriesRepository extends JpaRepository<Countries, Long>, JpaSpecificationExecutor<Countries> {

}
