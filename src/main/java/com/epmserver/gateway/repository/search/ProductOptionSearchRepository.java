package com.epmserver.gateway.repository.search;
import com.epmserver.gateway.domain.ProductOption;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link ProductOption} entity.
 */
public interface ProductOptionSearchRepository extends ElasticsearchRepository<ProductOption, Long> {
}
