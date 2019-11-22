package com.epmserver.gateway.repository.search;
import com.epmserver.gateway.domain.UploadTransactions;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link UploadTransactions} entity.
 */
public interface UploadTransactionsSearchRepository extends ElasticsearchRepository<UploadTransactions, Long> {
}
