package com.epmserver.gateway.repository.search;
import com.epmserver.gateway.domain.ProductTags;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link ProductTags} entity.
 */
public interface ProductTagsSearchRepository extends ElasticsearchRepository<ProductTags, Long> {
}
