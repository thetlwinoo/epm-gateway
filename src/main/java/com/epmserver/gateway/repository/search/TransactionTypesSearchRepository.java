package com.epmserver.gateway.repository.search;
import com.epmserver.gateway.domain.TransactionTypes;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link TransactionTypes} entity.
 */
public interface TransactionTypesSearchRepository extends ElasticsearchRepository<TransactionTypes, Long> {
}
