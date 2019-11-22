package com.epmserver.gateway.repository.search;
import com.epmserver.gateway.domain.WarrantyTypes;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link WarrantyTypes} entity.
 */
public interface WarrantyTypesSearchRepository extends ElasticsearchRepository<WarrantyTypes, Long> {
}
