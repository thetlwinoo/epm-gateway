package com.epmserver.gateway.repository.search;
import com.epmserver.gateway.domain.CompareProducts;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link CompareProducts} entity.
 */
public interface CompareProductsSearchRepository extends ElasticsearchRepository<CompareProducts, Long> {
}
