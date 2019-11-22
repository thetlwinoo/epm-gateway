package com.epmserver.gateway.repository.search;
import com.epmserver.gateway.domain.ShoppingCartItems;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link ShoppingCartItems} entity.
 */
public interface ShoppingCartItemsSearchRepository extends ElasticsearchRepository<ShoppingCartItems, Long> {
}
