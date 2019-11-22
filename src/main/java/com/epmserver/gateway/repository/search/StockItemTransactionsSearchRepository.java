package com.epmserver.gateway.repository.search;
import com.epmserver.gateway.domain.StockItemTransactions;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link StockItemTransactions} entity.
 */
public interface StockItemTransactionsSearchRepository extends ElasticsearchRepository<StockItemTransactions, Long> {
}
