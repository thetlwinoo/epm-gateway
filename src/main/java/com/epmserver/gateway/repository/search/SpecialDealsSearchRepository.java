package com.epmserver.gateway.repository.search;
import com.epmserver.gateway.domain.SpecialDeals;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link SpecialDeals} entity.
 */
public interface SpecialDealsSearchRepository extends ElasticsearchRepository<SpecialDeals, Long> {
}
