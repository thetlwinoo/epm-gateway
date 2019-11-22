package com.epmserver.gateway.repository.search;
import com.epmserver.gateway.domain.ProductOptionSet;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link ProductOptionSet} entity.
 */
public interface ProductOptionSetSearchRepository extends ElasticsearchRepository<ProductOptionSet, Long> {
}
