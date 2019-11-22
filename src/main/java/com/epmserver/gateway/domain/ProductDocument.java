package com.epmserver.gateway.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;

/**
 * A ProductDocument.
 */
@Entity
@Table(name = "product_document")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "productdocument")
public class ProductDocument implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Keyword)
    private Long id;

    @Column(name = "video_url")
    private String videoUrl;

    @Column(name = "highlights_url")
    private String highlightsUrl;

    @Column(name = "long_description_url")
    private String longDescriptionUrl;

    @Column(name = "short_description_url")
    private String shortDescriptionUrl;

    @Column(name = "description_url")
    private String descriptionUrl;

    @Column(name = "care_instructions_url")
    private String careInstructionsUrl;

    @Column(name = "special_features_url")
    private String specialFeaturesUrl;

    @Column(name = "usage_and_side_effects_url")
    private String usageAndSideEffectsUrl;

    @Column(name = "safety_warnning_url")
    private String safetyWarnningUrl;

    @Column(name = "product_type")
    private String productType;

    @Column(name = "model_name")
    private String modelName;

    @Column(name = "model_number")
    private String modelNumber;

    @Column(name = "fabric_type")
    private String fabricType;

    @Column(name = "product_compliance_certificate")
    private String productComplianceCertificate;

    @Column(name = "genuine_and_legal")
    private Boolean genuineAndLegal;

    @Column(name = "country_of_origin")
    private String countryOfOrigin;

    @Column(name = "warranty_period")
    private String warrantyPeriod;

    @Column(name = "warranty_policy")
    private String warrantyPolicy;

    @ManyToOne
    @JsonIgnoreProperties("productDocuments")
    private WarrantyTypes warrantyType;

    @ManyToOne
    @JsonIgnoreProperties("productDocuments")
    private Culture culture;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public ProductDocument videoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
        return this;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public String getHighlightsUrl() {
        return highlightsUrl;
    }

    public ProductDocument highlightsUrl(String highlightsUrl) {
        this.highlightsUrl = highlightsUrl;
        return this;
    }

    public void setHighlightsUrl(String highlightsUrl) {
        this.highlightsUrl = highlightsUrl;
    }

    public String getLongDescriptionUrl() {
        return longDescriptionUrl;
    }

    public ProductDocument longDescriptionUrl(String longDescriptionUrl) {
        this.longDescriptionUrl = longDescriptionUrl;
        return this;
    }

    public void setLongDescriptionUrl(String longDescriptionUrl) {
        this.longDescriptionUrl = longDescriptionUrl;
    }

    public String getShortDescriptionUrl() {
        return shortDescriptionUrl;
    }

    public ProductDocument shortDescriptionUrl(String shortDescriptionUrl) {
        this.shortDescriptionUrl = shortDescriptionUrl;
        return this;
    }

    public void setShortDescriptionUrl(String shortDescriptionUrl) {
        this.shortDescriptionUrl = shortDescriptionUrl;
    }

    public String getDescriptionUrl() {
        return descriptionUrl;
    }

    public ProductDocument descriptionUrl(String descriptionUrl) {
        this.descriptionUrl = descriptionUrl;
        return this;
    }

    public void setDescriptionUrl(String descriptionUrl) {
        this.descriptionUrl = descriptionUrl;
    }

    public String getCareInstructionsUrl() {
        return careInstructionsUrl;
    }

    public ProductDocument careInstructionsUrl(String careInstructionsUrl) {
        this.careInstructionsUrl = careInstructionsUrl;
        return this;
    }

    public void setCareInstructionsUrl(String careInstructionsUrl) {
        this.careInstructionsUrl = careInstructionsUrl;
    }

    public String getSpecialFeaturesUrl() {
        return specialFeaturesUrl;
    }

    public ProductDocument specialFeaturesUrl(String specialFeaturesUrl) {
        this.specialFeaturesUrl = specialFeaturesUrl;
        return this;
    }

    public void setSpecialFeaturesUrl(String specialFeaturesUrl) {
        this.specialFeaturesUrl = specialFeaturesUrl;
    }

    public String getUsageAndSideEffectsUrl() {
        return usageAndSideEffectsUrl;
    }

    public ProductDocument usageAndSideEffectsUrl(String usageAndSideEffectsUrl) {
        this.usageAndSideEffectsUrl = usageAndSideEffectsUrl;
        return this;
    }

    public void setUsageAndSideEffectsUrl(String usageAndSideEffectsUrl) {
        this.usageAndSideEffectsUrl = usageAndSideEffectsUrl;
    }

    public String getSafetyWarnningUrl() {
        return safetyWarnningUrl;
    }

    public ProductDocument safetyWarnningUrl(String safetyWarnningUrl) {
        this.safetyWarnningUrl = safetyWarnningUrl;
        return this;
    }

    public void setSafetyWarnningUrl(String safetyWarnningUrl) {
        this.safetyWarnningUrl = safetyWarnningUrl;
    }

    public String getProductType() {
        return productType;
    }

    public ProductDocument productType(String productType) {
        this.productType = productType;
        return this;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public String getModelName() {
        return modelName;
    }

    public ProductDocument modelName(String modelName) {
        this.modelName = modelName;
        return this;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public String getModelNumber() {
        return modelNumber;
    }

    public ProductDocument modelNumber(String modelNumber) {
        this.modelNumber = modelNumber;
        return this;
    }

    public void setModelNumber(String modelNumber) {
        this.modelNumber = modelNumber;
    }

    public String getFabricType() {
        return fabricType;
    }

    public ProductDocument fabricType(String fabricType) {
        this.fabricType = fabricType;
        return this;
    }

    public void setFabricType(String fabricType) {
        this.fabricType = fabricType;
    }

    public String getProductComplianceCertificate() {
        return productComplianceCertificate;
    }

    public ProductDocument productComplianceCertificate(String productComplianceCertificate) {
        this.productComplianceCertificate = productComplianceCertificate;
        return this;
    }

    public void setProductComplianceCertificate(String productComplianceCertificate) {
        this.productComplianceCertificate = productComplianceCertificate;
    }

    public Boolean isGenuineAndLegal() {
        return genuineAndLegal;
    }

    public ProductDocument genuineAndLegal(Boolean genuineAndLegal) {
        this.genuineAndLegal = genuineAndLegal;
        return this;
    }

    public void setGenuineAndLegal(Boolean genuineAndLegal) {
        this.genuineAndLegal = genuineAndLegal;
    }

    public String getCountryOfOrigin() {
        return countryOfOrigin;
    }

    public ProductDocument countryOfOrigin(String countryOfOrigin) {
        this.countryOfOrigin = countryOfOrigin;
        return this;
    }

    public void setCountryOfOrigin(String countryOfOrigin) {
        this.countryOfOrigin = countryOfOrigin;
    }

    public String getWarrantyPeriod() {
        return warrantyPeriod;
    }

    public ProductDocument warrantyPeriod(String warrantyPeriod) {
        this.warrantyPeriod = warrantyPeriod;
        return this;
    }

    public void setWarrantyPeriod(String warrantyPeriod) {
        this.warrantyPeriod = warrantyPeriod;
    }

    public String getWarrantyPolicy() {
        return warrantyPolicy;
    }

    public ProductDocument warrantyPolicy(String warrantyPolicy) {
        this.warrantyPolicy = warrantyPolicy;
        return this;
    }

    public void setWarrantyPolicy(String warrantyPolicy) {
        this.warrantyPolicy = warrantyPolicy;
    }

    public WarrantyTypes getWarrantyType() {
        return warrantyType;
    }

    public ProductDocument warrantyType(WarrantyTypes warrantyTypes) {
        this.warrantyType = warrantyTypes;
        return this;
    }

    public void setWarrantyType(WarrantyTypes warrantyTypes) {
        this.warrantyType = warrantyTypes;
    }

    public Culture getCulture() {
        return culture;
    }

    public ProductDocument culture(Culture culture) {
        this.culture = culture;
        return this;
    }

    public void setCulture(Culture culture) {
        this.culture = culture;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProductDocument)) {
            return false;
        }
        return id != null && id.equals(((ProductDocument) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "ProductDocument{" +
            "id=" + getId() +
            ", videoUrl='" + getVideoUrl() + "'" +
            ", highlightsUrl='" + getHighlightsUrl() + "'" +
            ", longDescriptionUrl='" + getLongDescriptionUrl() + "'" +
            ", shortDescriptionUrl='" + getShortDescriptionUrl() + "'" +
            ", descriptionUrl='" + getDescriptionUrl() + "'" +
            ", careInstructionsUrl='" + getCareInstructionsUrl() + "'" +
            ", specialFeaturesUrl='" + getSpecialFeaturesUrl() + "'" +
            ", usageAndSideEffectsUrl='" + getUsageAndSideEffectsUrl() + "'" +
            ", safetyWarnningUrl='" + getSafetyWarnningUrl() + "'" +
            ", productType='" + getProductType() + "'" +
            ", modelName='" + getModelName() + "'" +
            ", modelNumber='" + getModelNumber() + "'" +
            ", fabricType='" + getFabricType() + "'" +
            ", productComplianceCertificate='" + getProductComplianceCertificate() + "'" +
            ", genuineAndLegal='" + isGenuineAndLegal() + "'" +
            ", countryOfOrigin='" + getCountryOfOrigin() + "'" +
            ", warrantyPeriod='" + getWarrantyPeriod() + "'" +
            ", warrantyPolicy='" + getWarrantyPolicy() + "'" +
            "}";
    }
}
