package com.epmserver.gateway.repository.search;
import com.epmserver.gateway.domain.PersonEmailAddress;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link PersonEmailAddress} entity.
 */
public interface PersonEmailAddressSearchRepository extends ElasticsearchRepository<PersonEmailAddress, Long> {
}
