package com.epmserver.gateway.repository.search;
import com.epmserver.gateway.domain.DangerousGoods;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link DangerousGoods} entity.
 */
public interface DangerousGoodsSearchRepository extends ElasticsearchRepository<DangerousGoods, Long> {
}
