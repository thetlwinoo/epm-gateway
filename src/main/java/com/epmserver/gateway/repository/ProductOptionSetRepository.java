package com.epmserver.gateway.repository;
import com.epmserver.gateway.domain.ProductOptionSet;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ProductOptionSet entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProductOptionSetRepository extends JpaRepository<ProductOptionSet, Long>, JpaSpecificationExecutor<ProductOptionSet> {

}
