package com.epmserver.gateway.repository;
import com.epmserver.gateway.domain.PaymentTransactions;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the PaymentTransactions entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PaymentTransactionsRepository extends JpaRepository<PaymentTransactions, Long>, JpaSpecificationExecutor<PaymentTransactions> {

}
