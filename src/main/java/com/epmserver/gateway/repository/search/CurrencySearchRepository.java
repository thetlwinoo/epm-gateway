package com.epmserver.gateway.repository.search;
import com.epmserver.gateway.domain.Currency;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link Currency} entity.
 */
public interface CurrencySearchRepository extends ElasticsearchRepository<Currency, Long> {
}
