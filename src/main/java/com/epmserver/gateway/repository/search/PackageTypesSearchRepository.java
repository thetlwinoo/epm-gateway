package com.epmserver.gateway.repository.search;
import com.epmserver.gateway.domain.PackageTypes;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link PackageTypes} entity.
 */
public interface PackageTypesSearchRepository extends ElasticsearchRepository<PackageTypes, Long> {
}
