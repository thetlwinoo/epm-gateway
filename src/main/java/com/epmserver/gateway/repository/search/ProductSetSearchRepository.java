package com.epmserver.gateway.repository.search;
import com.epmserver.gateway.domain.ProductSet;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link ProductSet} entity.
 */
public interface ProductSetSearchRepository extends ElasticsearchRepository<ProductSet, Long> {
}
