package com.epmserver.gateway.repository.search;
import com.epmserver.gateway.domain.Invoices;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link Invoices} entity.
 */
public interface InvoicesSearchRepository extends ElasticsearchRepository<Invoices, Long> {
}
