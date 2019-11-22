package com.epmserver.gateway.repository;
import com.epmserver.gateway.domain.Suppliers;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Suppliers entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SuppliersRepository extends JpaRepository<Suppliers, Long>, JpaSpecificationExecutor<Suppliers> {

}
