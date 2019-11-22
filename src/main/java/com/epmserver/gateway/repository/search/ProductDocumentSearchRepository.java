package com.epmserver.gateway.repository.search;
import com.epmserver.gateway.domain.ProductDocument;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link ProductDocument} entity.
 */
public interface ProductDocumentSearchRepository extends ElasticsearchRepository<ProductDocument, Long> {
}
