package com.epmserver.gateway.repository.search;
import com.epmserver.gateway.domain.Cities;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link Cities} entity.
 */
public interface CitiesSearchRepository extends ElasticsearchRepository<Cities, Long> {
}
