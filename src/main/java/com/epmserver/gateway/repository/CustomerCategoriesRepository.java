package com.epmserver.gateway.repository;
import com.epmserver.gateway.domain.CustomerCategories;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the CustomerCategories entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CustomerCategoriesRepository extends JpaRepository<CustomerCategories, Long>, JpaSpecificationExecutor<CustomerCategories> {

}
