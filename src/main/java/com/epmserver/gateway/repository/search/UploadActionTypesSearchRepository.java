package com.epmserver.gateway.repository.search;
import com.epmserver.gateway.domain.UploadActionTypes;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link UploadActionTypes} entity.
 */
public interface UploadActionTypesSearchRepository extends ElasticsearchRepository<UploadActionTypes, Long> {
}
