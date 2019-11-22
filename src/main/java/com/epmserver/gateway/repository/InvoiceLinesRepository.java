package com.epmserver.gateway.repository;
import com.epmserver.gateway.domain.InvoiceLines;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the InvoiceLines entity.
 */
@SuppressWarnings("unused")
@Repository
public interface InvoiceLinesRepository extends JpaRepository<InvoiceLines, Long>, JpaSpecificationExecutor<InvoiceLines> {

}
