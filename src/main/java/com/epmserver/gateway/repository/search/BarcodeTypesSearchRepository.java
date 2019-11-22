package com.epmserver.gateway.repository.search;
import com.epmserver.gateway.domain.BarcodeTypes;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link BarcodeTypes} entity.
 */
public interface BarcodeTypesSearchRepository extends ElasticsearchRepository<BarcodeTypes, Long> {
}
