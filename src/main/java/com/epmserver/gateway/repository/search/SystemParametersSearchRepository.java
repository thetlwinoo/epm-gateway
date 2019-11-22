package com.epmserver.gateway.repository.search;
import com.epmserver.gateway.domain.SystemParameters;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link SystemParameters} entity.
 */
public interface SystemParametersSearchRepository extends ElasticsearchRepository<SystemParameters, Long> {
}
