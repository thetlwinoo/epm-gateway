package com.epmserver.gateway.repository.search;
import com.epmserver.gateway.domain.SupplierCategories;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link SupplierCategories} entity.
 */
public interface SupplierCategoriesSearchRepository extends ElasticsearchRepository<SupplierCategories, Long> {
}
