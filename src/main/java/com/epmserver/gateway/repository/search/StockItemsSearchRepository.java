package com.epmserver.gateway.repository.search;
import com.epmserver.gateway.domain.StockItems;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link StockItems} entity.
 */
public interface StockItemsSearchRepository extends ElasticsearchRepository<StockItems, Long> {
}
