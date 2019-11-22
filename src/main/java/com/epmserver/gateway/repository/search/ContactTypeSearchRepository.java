package com.epmserver.gateway.repository.search;
import com.epmserver.gateway.domain.ContactType;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link ContactType} entity.
 */
public interface ContactTypeSearchRepository extends ElasticsearchRepository<ContactType, Long> {
}
