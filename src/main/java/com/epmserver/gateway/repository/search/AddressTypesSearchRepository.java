package com.epmserver.gateway.repository.search;
import com.epmserver.gateway.domain.AddressTypes;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link AddressTypes} entity.
 */
public interface AddressTypesSearchRepository extends ElasticsearchRepository<AddressTypes, Long> {
}
