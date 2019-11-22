package com.epmserver.gateway.repository.search;
import com.epmserver.gateway.domain.Countries;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link Countries} entity.
 */
public interface CountriesSearchRepository extends ElasticsearchRepository<Countries, Long> {
}
