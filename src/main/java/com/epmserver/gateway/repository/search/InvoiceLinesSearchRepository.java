package com.epmserver.gateway.repository.search;
import com.epmserver.gateway.domain.InvoiceLines;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link InvoiceLines} entity.
 */
public interface InvoiceLinesSearchRepository extends ElasticsearchRepository<InvoiceLines, Long> {
}
