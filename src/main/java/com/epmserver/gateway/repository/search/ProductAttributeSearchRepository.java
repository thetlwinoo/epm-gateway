package com.epmserver.gateway.repository.search;
import com.epmserver.gateway.domain.ProductAttribute;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link ProductAttribute} entity.
 */
public interface ProductAttributeSearchRepository extends ElasticsearchRepository<ProductAttribute, Long> {
}
