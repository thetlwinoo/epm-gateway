package com.epmserver.gateway.repository.search;
import com.epmserver.gateway.domain.BusinessEntityContact;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link BusinessEntityContact} entity.
 */
public interface BusinessEntityContactSearchRepository extends ElasticsearchRepository<BusinessEntityContact, Long> {
}
