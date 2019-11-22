package com.epmserver.gateway.repository.search;
import com.epmserver.gateway.domain.ProductCatalog;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link ProductCatalog} entity.
 */
public interface ProductCatalogSearchRepository extends ElasticsearchRepository<ProductCatalog, Long> {
}
