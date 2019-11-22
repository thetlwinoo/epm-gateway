package com.epmserver.gateway.repository;
import com.epmserver.gateway.domain.BusinessEntityContact;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the BusinessEntityContact entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BusinessEntityContactRepository extends JpaRepository<BusinessEntityContact, Long>, JpaSpecificationExecutor<BusinessEntityContact> {

}
