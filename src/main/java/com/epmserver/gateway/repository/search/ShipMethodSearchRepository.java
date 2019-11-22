package com.epmserver.gateway.repository.search;
import com.epmserver.gateway.domain.ShipMethod;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link ShipMethod} entity.
 */
public interface ShipMethodSearchRepository extends ElasticsearchRepository<ShipMethod, Long> {
}
