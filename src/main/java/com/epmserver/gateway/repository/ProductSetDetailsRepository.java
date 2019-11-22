package com.epmserver.gateway.repository;
import com.epmserver.gateway.domain.ProductSetDetails;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ProductSetDetails entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProductSetDetailsRepository extends JpaRepository<ProductSetDetails, Long>, JpaSpecificationExecutor<ProductSetDetails> {

}
