package com.epmserver.gateway.repository.search;
import com.epmserver.gateway.domain.PurchaseOrderLines;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link PurchaseOrderLines} entity.
 */
public interface PurchaseOrderLinesSearchRepository extends ElasticsearchRepository<PurchaseOrderLines, Long> {
}
