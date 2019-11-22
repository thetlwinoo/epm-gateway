package com.epmserver.gateway.repository.search;
import com.epmserver.gateway.domain.ProductBrand;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link ProductBrand} entity.
 */
public interface ProductBrandSearchRepository extends ElasticsearchRepository<ProductBrand, Long> {
}
