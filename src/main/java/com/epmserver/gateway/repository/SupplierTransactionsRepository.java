package com.epmserver.gateway.repository;
import com.epmserver.gateway.domain.SupplierTransactions;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the SupplierTransactions entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SupplierTransactionsRepository extends JpaRepository<SupplierTransactions, Long>, JpaSpecificationExecutor<SupplierTransactions> {

}
