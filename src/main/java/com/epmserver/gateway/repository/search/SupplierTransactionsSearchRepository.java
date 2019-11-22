package com.epmserver.gateway.repository.search;
import com.epmserver.gateway.domain.SupplierTransactions;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link SupplierTransactions} entity.
 */
public interface SupplierTransactionsSearchRepository extends ElasticsearchRepository<SupplierTransactions, Long> {
}
