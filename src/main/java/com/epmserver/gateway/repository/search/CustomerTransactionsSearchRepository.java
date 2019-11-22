package com.epmserver.gateway.repository.search;
import com.epmserver.gateway.domain.CustomerTransactions;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link CustomerTransactions} entity.
 */
public interface CustomerTransactionsSearchRepository extends ElasticsearchRepository<CustomerTransactions, Long> {
}
