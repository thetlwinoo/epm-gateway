package com.epmserver.gateway.repository.search;
import com.epmserver.gateway.domain.PurchaseOrders;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link PurchaseOrders} entity.
 */
public interface PurchaseOrdersSearchRepository extends ElasticsearchRepository<PurchaseOrders, Long> {
}
