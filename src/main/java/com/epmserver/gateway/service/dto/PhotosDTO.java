package com.epmserver.gateway.service.dto;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.epmserver.gateway.domain.Photos} entity.
 */
public class PhotosDTO implements Serializable {

    private Long id;

    private String thumbnailImageUrl;

    private String originalImageUrl;

    private String bannerTallImageUrl;

    private String bannerWideImageUrl;

    private String circleImageUrl;

    private String sharpenedImageUrl;

    private String squareImageUrl;

    private String watermarkImageUrl;

    private Integer priority;

    private Boolean defaultInd;


    private Long stockItemId;

    private Long productCategoryId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getThumbnailImageUrl() {
        return thumbnailImageUrl;
    }

    public void setThumbnailImageUrl(String thumbnailImageUrl) {
        this.thumbnailImageUrl = thumbnailImageUrl;
    }

    public String getOriginalImageUrl() {
        return originalImageUrl;
    }

    public void setOriginalImageUrl(String originalImageUrl) {
        this.originalImageUrl = originalImageUrl;
    }

    public String getBannerTallImageUrl() {
        return bannerTallImageUrl;
    }

    public void setBannerTallImageUrl(String bannerTallImageUrl) {
        this.bannerTallImageUrl = bannerTallImageUrl;
    }

    public String getBannerWideImageUrl() {
        return bannerWideImageUrl;
    }

    public void setBannerWideImageUrl(String bannerWideImageUrl) {
        this.bannerWideImageUrl = bannerWideImageUrl;
    }

    public String getCircleImageUrl() {
        return circleImageUrl;
    }

    public void setCircleImageUrl(String circleImageUrl) {
        this.circleImageUrl = circleImageUrl;
    }

    public String getSharpenedImageUrl() {
        return sharpenedImageUrl;
    }

    public void setSharpenedImageUrl(String sharpenedImageUrl) {
        this.sharpenedImageUrl = sharpenedImageUrl;
    }

    public String getSquareImageUrl() {
        return squareImageUrl;
    }

    public void setSquareImageUrl(String squareImageUrl) {
        this.squareImageUrl = squareImageUrl;
    }

    public String getWatermarkImageUrl() {
        return watermarkImageUrl;
    }

    public void setWatermarkImageUrl(String watermarkImageUrl) {
        this.watermarkImageUrl = watermarkImageUrl;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public Boolean isDefaultInd() {
        return defaultInd;
    }

    public void setDefaultInd(Boolean defaultInd) {
        this.defaultInd = defaultInd;
    }

    public Long getStockItemId() {
        return stockItemId;
    }

    public void setStockItemId(Long stockItemsId) {
        this.stockItemId = stockItemsId;
    }

    public Long getProductCategoryId() {
        return productCategoryId;
    }

    public void setProductCategoryId(Long productCategoryId) {
        this.productCategoryId = productCategoryId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PhotosDTO photosDTO = (PhotosDTO) o;
        if (photosDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), photosDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PhotosDTO{" +
            "id=" + getId() +
            ", thumbnailImageUrl='" + getThumbnailImageUrl() + "'" +
            ", originalImageUrl='" + getOriginalImageUrl() + "'" +
            ", bannerTallImageUrl='" + getBannerTallImageUrl() + "'" +
            ", bannerWideImageUrl='" + getBannerWideImageUrl() + "'" +
            ", circleImageUrl='" + getCircleImageUrl() + "'" +
            ", sharpenedImageUrl='" + getSharpenedImageUrl() + "'" +
            ", squareImageUrl='" + getSquareImageUrl() + "'" +
            ", watermarkImageUrl='" + getWatermarkImageUrl() + "'" +
            ", priority=" + getPriority() +
            ", defaultInd='" + isDefaultInd() + "'" +
            ", stockItem=" + getStockItemId() +
            ", productCategory=" + getProductCategoryId() +
            "}";
    }
}
