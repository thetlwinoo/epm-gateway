package com.epmserver.gateway.repository.search;
import com.epmserver.gateway.domain.SupplierImportedDocument;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link SupplierImportedDocument} entity.
 */
public interface SupplierImportedDocumentSearchRepository extends ElasticsearchRepository<SupplierImportedDocument, Long> {
}
