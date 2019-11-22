package com.epmserver.gateway.repository;
import com.epmserver.gateway.domain.ShoppingCarts;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ShoppingCarts entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ShoppingCartsRepository extends JpaRepository<ShoppingCarts, Long>, JpaSpecificationExecutor<ShoppingCarts> {

}
