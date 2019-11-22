package com.epmserver.gateway.repository.search;
import com.epmserver.gateway.domain.Materials;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link Materials} entity.
 */
public interface MaterialsSearchRepository extends ElasticsearchRepository<Materials, Long> {
}
