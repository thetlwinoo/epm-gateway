package com.epmserver.gateway.repository.search;
import com.epmserver.gateway.domain.BusinessEntityAddress;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link BusinessEntityAddress} entity.
 */
public interface BusinessEntityAddressSearchRepository extends ElasticsearchRepository<BusinessEntityAddress, Long> {
}
