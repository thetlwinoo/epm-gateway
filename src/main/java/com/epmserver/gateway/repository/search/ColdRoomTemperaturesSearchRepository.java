package com.epmserver.gateway.repository.search;
import com.epmserver.gateway.domain.ColdRoomTemperatures;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link ColdRoomTemperatures} entity.
 */
public interface ColdRoomTemperaturesSearchRepository extends ElasticsearchRepository<ColdRoomTemperatures, Long> {
}
