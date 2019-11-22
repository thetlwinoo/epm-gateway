package com.epmserver.gateway.repository.search;
import com.epmserver.gateway.domain.UnitMeasure;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link UnitMeasure} entity.
 */
public interface UnitMeasureSearchRepository extends ElasticsearchRepository<UnitMeasure, Long> {
}
