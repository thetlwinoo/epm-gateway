package com.epmserver.gateway.repository.search;
import com.epmserver.gateway.domain.Addresses;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link Addresses} entity.
 */
public interface AddressesSearchRepository extends ElasticsearchRepository<Addresses, Long> {
}
