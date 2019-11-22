package com.epmserver.gateway.repository.search;
import com.epmserver.gateway.domain.PaymentTransactions;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link PaymentTransactions} entity.
 */
public interface PaymentTransactionsSearchRepository extends ElasticsearchRepository<PaymentTransactions, Long> {
}
