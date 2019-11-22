package com.epmserver.gateway.repository.search;
import com.epmserver.gateway.domain.Compares;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link Compares} entity.
 */
public interface ComparesSearchRepository extends ElasticsearchRepository<Compares, Long> {
}
