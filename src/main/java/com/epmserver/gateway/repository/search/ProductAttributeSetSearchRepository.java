package com.epmserver.gateway.repository.search;
import com.epmserver.gateway.domain.ProductAttributeSet;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link ProductAttributeSet} entity.
 */
public interface ProductAttributeSetSearchRepository extends ElasticsearchRepository<ProductAttributeSet, Long> {
}
