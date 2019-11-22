package com.epmserver.gateway.repository;
import com.epmserver.gateway.domain.DangerousGoods;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the DangerousGoods entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DangerousGoodsRepository extends JpaRepository<DangerousGoods, Long>, JpaSpecificationExecutor<DangerousGoods> {

}
