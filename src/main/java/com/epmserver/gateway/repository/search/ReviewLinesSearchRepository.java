package com.epmserver.gateway.repository.search;
import com.epmserver.gateway.domain.ReviewLines;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link ReviewLines} entity.
 */
public interface ReviewLinesSearchRepository extends ElasticsearchRepository<ReviewLines, Long> {
}
