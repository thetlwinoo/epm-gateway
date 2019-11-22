package com.epmserver.gateway.repository.search;
import com.epmserver.gateway.domain.CurrencyRate;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link CurrencyRate} entity.
 */
public interface CurrencyRateSearchRepository extends ElasticsearchRepository<CurrencyRate, Long> {
}
