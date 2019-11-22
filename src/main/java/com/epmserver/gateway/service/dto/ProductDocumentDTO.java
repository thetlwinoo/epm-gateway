package com.epmserver.gateway.service.dto;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.epmserver.gateway.domain.ProductDocument} entity.
 */
public class ProductDocumentDTO implements Serializable {

    private Long id;

    private String videoUrl;

    private String highlightsUrl;

    private String longDescriptionUrl;

    private String shortDescriptionUrl;

    private String descriptionUrl;

    private String careInstructionsUrl;

    private String specialFeaturesUrl;

    private String usageAndSideEffectsUrl;

    private String safetyWarnningUrl;

    private String productType;

    private String modelName;

    private String modelNumber;

    private String fabricType;

    private String productComplianceCertificate;

    private Boolean genuineAndLegal;

    private String countryOfOrigin;

    private String warrantyPeriod;

    private String warrantyPolicy;


    private Long warrantyTypeId;

    private String warrantyTypeName;

    private Long cultureId;

    private String cultureName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public String getHighlightsUrl() {
        return highlightsUrl;
    }

    public void setHighlightsUrl(String highlightsUrl) {
        this.highlightsUrl = highlightsUrl;
    }

    public String getLongDescriptionUrl() {
        return longDescriptionUrl;
    }

    public void setLongDescriptionUrl(String longDescriptionUrl) {
        this.longDescriptionUrl = longDescriptionUrl;
    }

    public String getShortDescriptionUrl() {
        return shortDescriptionUrl;
    }

    public void setShortDescriptionUrl(String shortDescriptionUrl) {
        this.shortDescriptionUrl = shortDescriptionUrl;
    }

    public String getDescriptionUrl() {
        return descriptionUrl;
    }

    public void setDescriptionUrl(String descriptionUrl) {
        this.descriptionUrl = descriptionUrl;
    }

    public String getCareInstructionsUrl() {
        return careInstructionsUrl;
    }

    public void setCareInstructionsUrl(String careInstructionsUrl) {
        this.careInstructionsUrl = careInstructionsUrl;
    }

    public String getSpecialFeaturesUrl() {
        return specialFeaturesUrl;
    }

    public void setSpecialFeaturesUrl(String specialFeaturesUrl) {
        this.specialFeaturesUrl = specialFeaturesUrl;
    }

    public String getUsageAndSideEffectsUrl() {
        return usageAndSideEffectsUrl;
    }

    public void setUsageAndSideEffectsUrl(String usageAndSideEffectsUrl) {
        this.usageAndSideEffectsUrl = usageAndSideEffectsUrl;
    }

    public String getSafetyWarnningUrl() {
        return safetyWarnningUrl;
    }

    public void setSafetyWarnningUrl(String safetyWarnningUrl) {
        this.safetyWarnningUrl = safetyWarnningUrl;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public String getModelNumber() {
        return modelNumber;
    }

    public void setModelNumber(String modelNumber) {
        this.modelNumber = modelNumber;
    }

    public String getFabricType() {
        return fabricType;
    }

    public void setFabricType(String fabricType) {
        this.fabricType = fabricType;
    }

    public String getProductComplianceCertificate() {
        return productComplianceCertificate;
    }

    public void setProductComplianceCertificate(String productComplianceCertificate) {
        this.productComplianceCertificate = productComplianceCertificate;
    }

    public Boolean isGenuineAndLegal() {
        return genuineAndLegal;
    }

    public void setGenuineAndLegal(Boolean genuineAndLegal) {
        this.genuineAndLegal = genuineAndLegal;
    }

    public String getCountryOfOrigin() {
        return countryOfOrigin;
    }

    public void setCountryOfOrigin(String countryOfOrigin) {
        this.countryOfOrigin = countryOfOrigin;
    }

    public String getWarrantyPeriod() {
        return warrantyPeriod;
    }

    public void setWarrantyPeriod(String warrantyPeriod) {
        this.warrantyPeriod = warrantyPeriod;
    }

    public String getWarrantyPolicy() {
        return warrantyPolicy;
    }

    public void setWarrantyPolicy(String warrantyPolicy) {
        this.warrantyPolicy = warrantyPolicy;
    }

    public Long getWarrantyTypeId() {
        return warrantyTypeId;
    }

    public void setWarrantyTypeId(Long warrantyTypesId) {
        this.warrantyTypeId = warrantyTypesId;
    }

    public String getWarrantyTypeName() {
        return warrantyTypeName;
    }

    public void setWarrantyTypeName(String warrantyTypesName) {
        this.warrantyTypeName = warrantyTypesName;
    }

    public Long getCultureId() {
        return cultureId;
    }

    public void setCultureId(Long cultureId) {
        this.cultureId = cultureId;
    }

    public String getCultureName() {
        return cultureName;
    }

    public void setCultureName(String cultureName) {
        this.cultureName = cultureName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ProductDocumentDTO productDocumentDTO = (ProductDocumentDTO) o;
        if (productDocumentDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), productDocumentDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ProductDocumentDTO{" +
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
            ", warrantyType=" + getWarrantyTypeId() +
            ", warrantyType='" + getWarrantyTypeName() + "'" +
            ", culture=" + getCultureId() +
            ", culture='" + getCultureName() + "'" +
            "}";
    }
}
