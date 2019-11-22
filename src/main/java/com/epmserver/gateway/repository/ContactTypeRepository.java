package com.epmserver.gateway.repository;
import com.epmserver.gateway.domain.ContactType;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ContactType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ContactTypeRepository extends JpaRepository<ContactType, Long>, JpaSpecificationExecutor<ContactType> {

}
