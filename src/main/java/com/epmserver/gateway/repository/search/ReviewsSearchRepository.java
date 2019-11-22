package com.epmserver.gateway.repository.search;
import com.epmserver.gateway.domain.Reviews;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link Reviews} entity.
 */
public interface ReviewsSearchRepository extends ElasticsearchRepository<Reviews, Long> {
}
