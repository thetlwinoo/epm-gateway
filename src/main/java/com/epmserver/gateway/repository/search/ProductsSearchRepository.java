package com.epmserver.gateway.repository.search;
import com.epmserver.gateway.domain.Products;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link Products} entity.
 */
public interface ProductsSearchRepository extends ElasticsearchRepository<Products, Long> {
}
