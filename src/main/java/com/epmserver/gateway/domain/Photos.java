package com.epmserver.gateway.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;

/**
 * A Photos.
 */
@Entity
@Table(name = "photos")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "photos")
public class Photos implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Keyword)
    private Long id;

    @Column(name = "thumbnail_image_url")
    private String thumbnailImageUrl;

    @Column(name = "original_image_url")
    private String originalImageUrl;

    @Column(name = "banner_tall_image_url")
    private String bannerTallImageUrl;

    @Column(name = "banner_wide_image_url")
    private String bannerWideImageUrl;

    @Column(name = "circle_image_url")
    private String circleImageUrl;

    @Column(name = "sharpened_image_url")
    private String sharpenedImageUrl;

    @Column(name = "square_image_url")
    private String squareImageUrl;

    @Column(name = "watermark_image_url")
    private String watermarkImageUrl;

    @Column(name = "priority")
    private Integer priority;

    @Column(name = "default_ind")
    private Boolean defaultInd;

    @ManyToOne
    @JsonIgnoreProperties("photoLists")
    private StockItems stockItem;

    @ManyToOne
    @JsonIgnoreProperties("photoLists")
    private ProductCategory productCategory;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getThumbnailImageUrl() {
        return thumbnailImageUrl;
    }

    public Photos thumbnailImageUrl(String thumbnailImageUrl) {
        this.thumbnailImageUrl = thumbnailImageUrl;
        return this;
    }

    public void setThumbnailImageUrl(String thumbnailImageUrl) {
        this.thumbnailImageUrl = thumbnailImageUrl;
    }

    public String getOriginalImageUrl() {
        return originalImageUrl;
    }

    public Photos originalImageUrl(String originalImageUrl) {
        this.originalImageUrl = originalImageUrl;
        return this;
    }

    public void setOriginalImageUrl(String originalImageUrl) {
        this.originalImageUrl = originalImageUrl;
    }

    public String getBannerTallImageUrl() {
        return bannerTallImageUrl;
    }

    public Photos bannerTallImageUrl(String bannerTallImageUrl) {
        this.bannerTallImageUrl = bannerTallImageUrl;
        return this;
    }

    public void setBannerTallImageUrl(String bannerTallImageUrl) {
        this.bannerTallImageUrl = bannerTallImageUrl;
    }

    public String getBannerWideImageUrl() {
        return bannerWideImageUrl;
    }

    public Photos bannerWideImageUrl(String bannerWideImageUrl) {
        this.bannerWideImageUrl = bannerWideImageUrl;
        return this;
    }

    public void setBannerWideImageUrl(String bannerWideImageUrl) {
        this.bannerWideImageUrl = bannerWideImageUrl;
    }

    public String getCircleImageUrl() {
        return circleImageUrl;
    }

    public Photos circleImageUrl(String circleImageUrl) {
        this.circleImageUrl = circleImageUrl;
        return this;
    }

    public void setCircleImageUrl(String circleImageUrl) {
        this.circleImageUrl = circleImageUrl;
    }

    public String getSharpenedImageUrl() {
        return sharpenedImageUrl;
    }

    public Photos sharpenedImageUrl(String sharpenedImageUrl) {
        this.sharpenedImageUrl = sharpenedImageUrl;
        return this;
    }

    public void setSharpenedImageUrl(String sharpenedImageUrl) {
        this.sharpenedImageUrl = sharpenedImageUrl;
    }

    public String getSquareImageUrl() {
        return squareImageUrl;
    }

    public Photos squareImageUrl(String squareImageUrl) {
        this.squareImageUrl = squareImageUrl;
        return this;
    }

    public void setSquareImageUrl(String squareImageUrl) {
        this.squareImageUrl = squareImageUrl;
    }

    public String getWatermarkImageUrl() {
        return watermarkImageUrl;
    }

    public Photos watermarkImageUrl(String watermarkImageUrl) {
        this.watermarkImageUrl = watermarkImageUrl;
        return this;
    }

    public void setWatermarkImageUrl(String watermarkImageUrl) {
        this.watermarkImageUrl = watermarkImageUrl;
    }

    public Integer getPriority() {
        return priority;
    }

    public Photos priority(Integer priority) {
        this.priority = priority;
        return this;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public Boolean isDefaultInd() {
        return defaultInd;
    }

    public Photos defaultInd(Boolean defaultInd) {
        this.defaultInd = defaultInd;
        return this;
    }

    public void setDefaultInd(Boolean defaultInd) {
        this.defaultInd = defaultInd;
    }

    public StockItems getStockItem() {
        return stockItem;
    }

    public Photos stockItem(StockItems stockItems) {
        this.stockItem = stockItems;
        return this;
    }

    public void setStockItem(StockItems stockItems) {
        this.stockItem = stockItems;
    }

    public ProductCategory getProductCategory() {
        return productCategory;
    }

    public Photos productCategory(ProductCategory productCategory) {
        this.productCategory = productCategory;
        return this;
    }

    public void setProductCategory(ProductCategory productCategory) {
        this.productCategory = productCategory;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Photos)) {
            return false;
        }
        return id != null && id.equals(((Photos) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Photos{" +
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
            "}";
    }
}
