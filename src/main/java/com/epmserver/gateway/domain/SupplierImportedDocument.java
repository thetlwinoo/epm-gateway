package com.epmserver.gateway.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.time.Instant;

import com.epmserver.gateway.domain.enumeration.DocumentType;

/**
 * A SupplierImportedDocument.
 */
@Entity
@Table(name = "supplier_imported_document")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "supplierimporteddocument")
public class SupplierImportedDocument implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Keyword)
    private Long id;

    @Column(name = "document_url")
    private String documentUrl;

    @Enumerated(EnumType.STRING)
    @Column(name = "document_type")
    private DocumentType documentType;

    @Column(name = "last_edited_by")
    private String lastEditedBy;

    @Column(name = "last_edited_when")
    private Instant lastEditedWhen;

    @ManyToOne
    @JsonIgnoreProperties("importDocumentLists")
    private UploadTransactions uploadTransaction;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDocumentUrl() {
        return documentUrl;
    }

    public SupplierImportedDocument documentUrl(String documentUrl) {
        this.documentUrl = documentUrl;
        return this;
    }

    public void setDocumentUrl(String documentUrl) {
        this.documentUrl = documentUrl;
    }

    public DocumentType getDocumentType() {
        return documentType;
    }

    public SupplierImportedDocument documentType(DocumentType documentType) {
        this.documentType = documentType;
        return this;
    }

    public void setDocumentType(DocumentType documentType) {
        this.documentType = documentType;
    }

    public String getLastEditedBy() {
        return lastEditedBy;
    }

    public SupplierImportedDocument lastEditedBy(String lastEditedBy) {
        this.lastEditedBy = lastEditedBy;
        return this;
    }

    public void setLastEditedBy(String lastEditedBy) {
        this.lastEditedBy = lastEditedBy;
    }

    public Instant getLastEditedWhen() {
        return lastEditedWhen;
    }

    public SupplierImportedDocument lastEditedWhen(Instant lastEditedWhen) {
        this.lastEditedWhen = lastEditedWhen;
        return this;
    }

    public void setLastEditedWhen(Instant lastEditedWhen) {
        this.lastEditedWhen = lastEditedWhen;
    }

    public UploadTransactions getUploadTransaction() {
        return uploadTransaction;
    }

    public SupplierImportedDocument uploadTransaction(UploadTransactions uploadTransactions) {
        this.uploadTransaction = uploadTransactions;
        return this;
    }

    public void setUploadTransaction(UploadTransactions uploadTransactions) {
        this.uploadTransaction = uploadTransactions;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SupplierImportedDocument)) {
            return false;
        }
        return id != null && id.equals(((SupplierImportedDocument) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "SupplierImportedDocument{" +
            "id=" + getId() +
            ", documentUrl='" + getDocumentUrl() + "'" +
            ", documentType='" + getDocumentType() + "'" +
            ", lastEditedBy='" + getLastEditedBy() + "'" +
            ", lastEditedWhen='" + getLastEditedWhen() + "'" +
            "}";
    }
}
