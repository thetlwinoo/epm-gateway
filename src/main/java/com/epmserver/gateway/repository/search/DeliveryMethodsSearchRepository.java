package com.epmserver.gateway.repository.search;
import com.epmserver.gateway.domain.DeliveryMethods;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link DeliveryMethods} entity.
 */
public interface DeliveryMethodsSearchRepository extends ElasticsearchRepository<DeliveryMethods, Long> {
}
