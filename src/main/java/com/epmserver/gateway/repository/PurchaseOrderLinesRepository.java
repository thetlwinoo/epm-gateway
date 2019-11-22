package com.epmserver.gateway.repository;
import com.epmserver.gateway.domain.PurchaseOrderLines;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the PurchaseOrderLines entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PurchaseOrderLinesRepository extends JpaRepository<PurchaseOrderLines, Long>, JpaSpecificationExecutor<PurchaseOrderLines> {

}
