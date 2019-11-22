package com.epmserver.gateway.repository.search;
import com.epmserver.gateway.domain.Suppliers;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link Suppliers} entity.
 */
public interface SuppliersSearchRepository extends ElasticsearchRepository<Suppliers, Long> {
}
