package com.epmserver.gateway.repository;
import com.epmserver.gateway.domain.Culture;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Culture entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CultureRepository extends JpaRepository<Culture, Long>, JpaSpecificationExecutor<Culture> {

}
