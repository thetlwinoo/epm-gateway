package com.epmserver.gateway.repository.search;
import com.epmserver.gateway.domain.VehicleTemperatures;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link VehicleTemperatures} entity.
 */
public interface VehicleTemperaturesSearchRepository extends ElasticsearchRepository<VehicleTemperatures, Long> {
}
