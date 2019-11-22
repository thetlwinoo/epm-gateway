package com.epmserver.gateway.repository.search;
import com.epmserver.gateway.domain.WishlistProducts;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link WishlistProducts} entity.
 */
public interface WishlistProductsSearchRepository extends ElasticsearchRepository<WishlistProducts, Long> {
}
