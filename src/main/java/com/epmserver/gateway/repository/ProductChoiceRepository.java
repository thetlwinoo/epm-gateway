package com.epmserver.gateway.repository;
import com.epmserver.gateway.domain.ProductChoice;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ProductChoice entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProductChoiceRepository extends JpaRepository<ProductChoice, Long>, JpaSpecificationExecutor<ProductChoice> {

}
