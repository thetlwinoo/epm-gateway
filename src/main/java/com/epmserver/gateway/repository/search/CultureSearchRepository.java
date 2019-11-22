package com.epmserver.gateway.repository.search;
import com.epmserver.gateway.domain.Culture;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link Culture} entity.
 */
public interface CultureSearchRepository extends ElasticsearchRepository<Culture, Long> {
}
