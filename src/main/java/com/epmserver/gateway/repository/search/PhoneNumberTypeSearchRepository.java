package com.epmserver.gateway.repository.search;
import com.epmserver.gateway.domain.PhoneNumberType;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link PhoneNumberType} entity.
 */
public interface PhoneNumberTypeSearchRepository extends ElasticsearchRepository<PhoneNumberType, Long> {
}
