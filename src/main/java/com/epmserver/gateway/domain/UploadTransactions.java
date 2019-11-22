package com.epmserver.gateway.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

import com.epmserver.gateway.domain.enumeration.UploadTransactionStatus;

/**
 * A UploadTransactions.
 */
@Entity
@Table(name = "upload_transactions")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "uploadtransactions")
public class UploadTransactions implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Keyword)
    private Long id;

    @Column(name = "file_name")
    private String fileName;

    @Column(name = "template_url")
    private String templateUrl;

    @Column(name = "generated_code")
    private String generatedCode;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private UploadTransactionStatus status;

    @Column(name = "last_edited_by")
    private String lastEditedBy;

    @Column(name = "last_edited_when")
    private Instant lastEditedWhen;

    @OneToMany(mappedBy = "uploadTransaction")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<SupplierImportedDocument> importDocumentLists = new HashSet<>();

    @OneToMany(mappedBy = "uploadTransaction")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<StockItemTemp> stockItemTempLists = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("uploadTransactions")
    private Suppliers supplier;

    @ManyToOne
    @JsonIgnoreProperties("uploadTransactions")
    private UploadActionTypes actionType;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public UploadTransactions fileName(String fileName) {
        this.fileName = fileName;
        return this;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getTemplateUrl() {
        return templateUrl;
    }

    public UploadTransactions templateUrl(String templateUrl) {
        this.templateUrl = templateUrl;
        return this;
    }

    public void setTemplateUrl(String templateUrl) {
        this.templateUrl = templateUrl;
    }

    public String getGeneratedCode() {
        return generatedCode;
    }

    public UploadTransactions generatedCode(String generatedCode) {
        this.generatedCode = generatedCode;
        return this;
    }

    public void setGeneratedCode(String generatedCode) {
        this.generatedCode = generatedCode;
    }

    public UploadTransactionStatus getStatus() {
        return status;
    }

    public UploadTransactions status(UploadTransactionStatus status) {
        this.status = status;
        return this;
    }

    public void setStatus(UploadTransactionStatus status) {
        this.status = status;
    }

    public String getLastEditedBy() {
        return lastEditedBy;
    }

    public UploadTransactions lastEditedBy(String lastEditedBy) {
        this.lastEditedBy = lastEditedBy;
        return this;
    }

    public void setLastEditedBy(String lastEditedBy) {
        this.lastEditedBy = lastEditedBy;
    }

    public Instant getLastEditedWhen() {
        return lastEditedWhen;
    }

    public UploadTransactions lastEditedWhen(Instant lastEditedWhen) {
        this.lastEditedWhen = lastEditedWhen;
        return this;
    }

    public void setLastEditedWhen(Instant lastEditedWhen) {
        this.lastEditedWhen = lastEditedWhen;
    }

    public Set<SupplierImportedDocument> getImportDocumentLists() {
        return importDocumentLists;
    }

    public UploadTransactions importDocumentLists(Set<SupplierImportedDocument> supplierImportedDocuments) {
        this.importDocumentLists = supplierImportedDocuments;
        return this;
    }

    public UploadTransactions addImportDocumentList(SupplierImportedDocument supplierImportedDocument) {
        this.importDocumentLists.add(supplierImportedDocument);
        supplierImportedDocument.setUploadTransaction(this);
        return this;
    }

    public UploadTransactions removeImportDocumentList(SupplierImportedDocument supplierImportedDocument) {
        this.importDocumentLists.remove(supplierImportedDocument);
        supplierImportedDocument.setUploadTransaction(null);
        return this;
    }

    public void setImportDocumentLists(Set<SupplierImportedDocument> supplierImportedDocuments) {
        this.importDocumentLists = supplierImportedDocuments;
    }

    public Set<StockItemTemp> getStockItemTempLists() {
        return stockItemTempLists;
    }

    public UploadTransactions stockItemTempLists(Set<StockItemTemp> stockItemTemps) {
        this.stockItemTempLists = stockItemTemps;
        return this;
    }

    public UploadTransactions addStockItemTempList(StockItemTemp stockItemTemp) {
        this.stockItemTempLists.add(stockItemTemp);
        stockItemTemp.setUploadTransaction(this);
        return this;
    }

    public UploadTransactions removeStockItemTempList(StockItemTemp stockItemTemp) {
        this.stockItemTempLists.remove(stockItemTemp);
        stockItemTemp.setUploadTransaction(null);
        return this;
    }

    public void setStockItemTempLists(Set<StockItemTemp> stockItemTemps) {
        this.stockItemTempLists = stockItemTemps;
    }

    public Suppliers getSupplier() {
        return supplier;
    }

    public UploadTransactions supplier(Suppliers suppliers) {
        this.supplier = suppliers;
        return this;
    }

    public void setSupplier(Suppliers suppliers) {
        this.supplier = suppliers;
    }

    public UploadActionTypes getActionType() {
        return actionType;
    }

    public UploadTransactions actionType(UploadActionTypes uploadActionTypes) {
        this.actionType = uploadActionTypes;
        return this;
    }

    public void setActionType(UploadActionTypes uploadActionTypes) {
        this.actionType = uploadActionTypes;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UploadTransactions)) {
            return false;
        }
        return id != null && id.equals(((UploadTransactions) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "UploadTransactions{" +
            "id=" + getId() +
            ", fileName='" + getFileName() + "'" +
            ", templateUrl='" + getTemplateUrl() + "'" +
            ", generatedCode='" + getGeneratedCode() + "'" +
            ", status='" + getStatus() + "'" +
            ", lastEditedBy='" + getLastEditedBy() + "'" +
            ", lastEditedWhen='" + getLastEditedWhen() + "'" +
            "}";
    }
}
