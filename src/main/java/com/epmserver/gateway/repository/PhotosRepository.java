package com.epmserver.gateway.repository;
import com.epmserver.gateway.domain.Photos;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Photos entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PhotosRepository extends JpaRepository<Photos, Long>, JpaSpecificationExecutor<Photos> {

}
