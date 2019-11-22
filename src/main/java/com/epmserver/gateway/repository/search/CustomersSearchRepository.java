package com.epmserver.gateway.repository.search;
import com.epmserver.gateway.domain.Customers;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link Customers} entity.
 */
public interface CustomersSearchRepository extends ElasticsearchRepository<Customers, Long> {
}
