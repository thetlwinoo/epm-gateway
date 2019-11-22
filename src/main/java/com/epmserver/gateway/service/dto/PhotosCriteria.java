package com.epmserver.gateway.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link com.epmserver.gateway.domain.Photos} entity. This class is used
 * in {@link com.epmserver.gateway.web.rest.PhotosResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /photos?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class PhotosCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter thumbnailImageUrl;

    private StringFilter originalImageUrl;

    private StringFilter bannerTallImageUrl;

    private StringFilter bannerWideImageUrl;

    private StringFilter circleImageUrl;

    private StringFilter sharpenedImageUrl;

    private StringFilter squareImageUrl;

    private StringFilter watermarkImageUrl;

    private IntegerFilter priority;

    private BooleanFilter defaultInd;

    private LongFilter stockItemId;

    private LongFilter productCategoryId;

    public PhotosCriteria(){
    }

    public PhotosCriteria(PhotosCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.thumbnailImageUrl = other.thumbnailImageUrl == null ? null : other.thumbnailImageUrl.copy();
        this.originalImageUrl = other.originalImageUrl == null ? null : other.originalImageUrl.copy();
        this.bannerTallImageUrl = other.bannerTallImageUrl == null ? null : other.bannerTallImageUrl.copy();
        this.bannerWideImageUrl = other.bannerWideImageUrl == null ? null : other.bannerWideImageUrl.copy();
        this.circleImageUrl = other.circleImageUrl == null ? null : other.circleImageUrl.copy();
        this.sharpenedImageUrl = other.sharpenedImageUrl == null ? null : other.sharpenedImageUrl.copy();
        this.squareImageUrl = other.squareImageUrl == null ? null : other.squareImageUrl.copy();
        this.watermarkImageUrl = other.watermarkImageUrl == null ? null : other.watermarkImageUrl.copy();
        this.priority = other.priority == null ? null : other.priority.copy();
        this.defaultInd = other.defaultInd == null ? null : other.defaultInd.copy();
        this.stockItemId = other.stockItemId == null ? null : other.stockItemId.copy();
        this.productCategoryId = other.productCategoryId == null ? null : other.productCategoryId.copy();
    }

    @Override
    public PhotosCriteria copy() {
        return new PhotosCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getThumbnailImageUrl() {
        return thumbnailImageUrl;
    }

    public void setThumbnailImageUrl(StringFilter thumbnailImageUrl) {
        this.thumbnailImageUrl = thumbnailImageUrl;
    }

    public StringFilter getOriginalImageUrl() {
        return originalImageUrl;
    }

    public void setOriginalImageUrl(StringFilter originalImageUrl) {
        this.originalImageUrl = originalImageUrl;
    }

    public StringFilter getBannerTallImageUrl() {
        return bannerTallImageUrl;
    }

    public void setBannerTallImageUrl(StringFilter bannerTallImageUrl) {
        this.bannerTallImageUrl = bannerTallImageUrl;
    }

    public StringFilter getBannerWideImageUrl() {
        return bannerWideImageUrl;
    }

    public void setBannerWideImageUrl(StringFilter bannerWideImageUrl) {
        this.bannerWideImageUrl = bannerWideImageUrl;
    }

    public StringFilter getCircleImageUrl() {
        return circleImageUrl;
    }

    public void setCircleImageUrl(StringFilter circleImageUrl) {
        this.circleImageUrl = circleImageUrl;
    }

    public StringFilter getSharpenedImageUrl() {
        return sharpenedImageUrl;
    }

    public void setSharpenedImageUrl(StringFilter sharpenedImageUrl) {
        this.sharpenedImageUrl = sharpenedImageUrl;
    }

    public StringFilter getSquareImageUrl() {
        return squareImageUrl;
    }

    public void setSquareImageUrl(StringFilter squareImageUrl) {
        this.squareImageUrl = squareImageUrl;
    }

    public StringFilter getWatermarkImageUrl() {
        return watermarkImageUrl;
    }

    public void setWatermarkImageUrl(StringFilter watermarkImageUrl) {
        this.watermarkImageUrl = watermarkImageUrl;
    }

    public IntegerFilter getPriority() {
        return priority;
    }

    public void setPriority(IntegerFilter priority) {
        this.priority = priority;
    }

    public BooleanFilter getDefaultInd() {
        return defaultInd;
    }

    public void setDefaultInd(BooleanFilter defaultInd) {
        this.defaultInd = defaultInd;
    }

    public LongFilter getStockItemId() {
        return stockItemId;
    }

    public void setStockItemId(LongFilter stockItemId) {
        this.stockItemId = stockItemId;
    }

    public LongFilter getProductCategoryId() {
        return productCategoryId;
    }

    public void setProductCategoryId(LongFilter productCategoryId) {
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
        final PhotosCriteria that = (PhotosCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(thumbnailImageUrl, that.thumbnailImageUrl) &&
            Objects.equals(originalImageUrl, that.originalImageUrl) &&
            Objects.equals(bannerTallImageUrl, that.bannerTallImageUrl) &&
            Objects.equals(bannerWideImageUrl, that.bannerWideImageUrl) &&
            Objects.equals(circleImageUrl, that.circleImageUrl) &&
            Objects.equals(sharpenedImageUrl, that.sharpenedImageUrl) &&
            Objects.equals(squareImageUrl, that.squareImageUrl) &&
            Objects.equals(watermarkImageUrl, that.watermarkImageUrl) &&
            Objects.equals(priority, that.priority) &&
            Objects.equals(defaultInd, that.defaultInd) &&
            Objects.equals(stockItemId, that.stockItemId) &&
            Objects.equals(productCategoryId, that.productCategoryId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        thumbnailImageUrl,
        originalImageUrl,
        bannerTallImageUrl,
        bannerWideImageUrl,
        circleImageUrl,
        sharpenedImageUrl,
        squareImageUrl,
        watermarkImageUrl,
        priority,
        defaultInd,
        stockItemId,
        productCategoryId
        );
    }

    @Override
    public String toString() {
        return "PhotosCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (thumbnailImageUrl != null ? "thumbnailImageUrl=" + thumbnailImageUrl + ", " : "") +
                (originalImageUrl != null ? "originalImageUrl=" + originalImageUrl + ", " : "") +
                (bannerTallImageUrl != null ? "bannerTallImageUrl=" + bannerTallImageUrl + ", " : "") +
                (bannerWideImageUrl != null ? "bannerWideImageUrl=" + bannerWideImageUrl + ", " : "") +
                (circleImageUrl != null ? "circleImageUrl=" + circleImageUrl + ", " : "") +
                (sharpenedImageUrl != null ? "sharpenedImageUrl=" + sharpenedImageUrl + ", " : "") +
                (squareImageUrl != null ? "squareImageUrl=" + squareImageUrl + ", " : "") +
                (watermarkImageUrl != null ? "watermarkImageUrl=" + watermarkImageUrl + ", " : "") +
                (priority != null ? "priority=" + priority + ", " : "") +
                (defaultInd != null ? "defaultInd=" + defaultInd + ", " : "") +
                (stockItemId != null ? "stockItemId=" + stockItemId + ", " : "") +
                (productCategoryId != null ? "productCategoryId=" + productCategoryId + ", " : "") +
            "}";
    }

}
