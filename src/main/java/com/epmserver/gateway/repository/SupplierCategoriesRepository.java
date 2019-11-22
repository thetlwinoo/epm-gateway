package com.epmserver.gateway.repository;
import com.epmserver.gateway.domain.SupplierCategories;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the SupplierCategories entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SupplierCategoriesRepository extends JpaRepository<SupplierCategories, Long>, JpaSpecificationExecutor<SupplierCategories> {

}
