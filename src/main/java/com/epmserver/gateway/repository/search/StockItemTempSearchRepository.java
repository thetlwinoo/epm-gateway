package com.epmserver.gateway.repository.search;
import com.epmserver.gateway.domain.StockItemTemp;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link StockItemTemp} entity.
 */
public interface StockItemTempSearchRepository extends ElasticsearchRepository<StockItemTemp, Long> {
}
