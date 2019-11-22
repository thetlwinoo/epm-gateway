package com.epmserver.gateway.repository.search;
import com.epmserver.gateway.domain.CustomerCategories;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link CustomerCategories} entity.
 */
public interface CustomerCategoriesSearchRepository extends ElasticsearchRepository<CustomerCategories, Long> {
}
