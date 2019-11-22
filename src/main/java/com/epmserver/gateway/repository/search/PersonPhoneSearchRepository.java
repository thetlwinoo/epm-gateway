package com.epmserver.gateway.repository.search;
import com.epmserver.gateway.domain.PersonPhone;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link PersonPhone} entity.
 */
public interface PersonPhoneSearchRepository extends ElasticsearchRepository<PersonPhone, Long> {
}
