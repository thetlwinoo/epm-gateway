package com.epmserver.gateway.repository.search;
import com.epmserver.gateway.domain.ProductChoice;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link ProductChoice} entity.
 */
public interface ProductChoiceSearchRepository extends ElasticsearchRepository<ProductChoice, Long> {
}
