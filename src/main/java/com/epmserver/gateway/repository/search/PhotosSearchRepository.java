package com.epmserver.gateway.repository.search;
import com.epmserver.gateway.domain.Photos;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link Photos} entity.
 */
public interface PhotosSearchRepository extends ElasticsearchRepository<Photos, Long> {
}
