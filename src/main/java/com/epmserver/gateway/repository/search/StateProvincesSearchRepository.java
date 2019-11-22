package com.epmserver.gateway.repository.search;
import com.epmserver.gateway.domain.StateProvinces;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link StateProvinces} entity.
 */
public interface StateProvincesSearchRepository extends ElasticsearchRepository<StateProvinces, Long> {
}
