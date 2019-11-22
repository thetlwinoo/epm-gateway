package com.epmserver.gateway.repository.search;
import com.epmserver.gateway.domain.BuyingGroups;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link BuyingGroups} entity.
 */
public interface BuyingGroupsSearchRepository extends ElasticsearchRepository<BuyingGroups, Long> {
}
