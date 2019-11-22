package com.epmserver.gateway.repository;
import com.epmserver.gateway.domain.Customers;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Customers entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CustomersRepository extends JpaRepository<Customers, Long>, JpaSpecificationExecutor<Customers> {

}
