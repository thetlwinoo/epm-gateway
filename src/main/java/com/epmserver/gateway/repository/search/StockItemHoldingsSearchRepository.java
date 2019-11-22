package com.epmserver.gateway.repository.search;
import com.epmserver.gateway.domain.StockItemHoldings;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link StockItemHoldings} entity.
 */
public interface StockItemHoldingsSearchRepository extends ElasticsearchRepository<StockItemHoldings, Long> {
}
