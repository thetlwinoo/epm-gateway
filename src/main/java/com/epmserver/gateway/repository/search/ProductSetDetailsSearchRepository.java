package com.epmserver.gateway.repository.search;
import com.epmserver.gateway.domain.ProductSetDetails;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link ProductSetDetails} entity.
 */
public interface ProductSetDetailsSearchRepository extends ElasticsearchRepository<ProductSetDetails, Long> {
}
