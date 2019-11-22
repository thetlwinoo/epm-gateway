package com.epmserver.gateway.repository.search;
import com.epmserver.gateway.domain.ShoppingCarts;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link ShoppingCarts} entity.
 */
public interface ShoppingCartsSearchRepository extends ElasticsearchRepository<ShoppingCarts, Long> {
}
