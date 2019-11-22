package com.epmserver.gateway.repository.search;
import com.epmserver.gateway.domain.OrderLines;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link OrderLines} entity.
 */
public interface OrderLinesSearchRepository extends ElasticsearchRepository<OrderLines, Long> {
}
