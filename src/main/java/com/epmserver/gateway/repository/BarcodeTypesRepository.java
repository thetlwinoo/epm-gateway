package com.epmserver.gateway.repository;
import com.epmserver.gateway.domain.BarcodeTypes;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the BarcodeTypes entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BarcodeTypesRepository extends JpaRepository<BarcodeTypes, Long>, JpaSpecificationExecutor<BarcodeTypes> {

}
