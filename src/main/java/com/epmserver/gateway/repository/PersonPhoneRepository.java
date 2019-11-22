package com.epmserver.gateway.repository;
import com.epmserver.gateway.domain.PersonPhone;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the PersonPhone entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PersonPhoneRepository extends JpaRepository<PersonPhone, Long>, JpaSpecificationExecutor<PersonPhone> {

}
