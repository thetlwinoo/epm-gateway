package com.epmserver.gateway.repository.search;
import com.epmserver.gateway.domain.Wishlists;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link Wishlists} entity.
 */
public interface WishlistsSearchRepository extends ElasticsearchRepository<Wishlists, Long> {
}
