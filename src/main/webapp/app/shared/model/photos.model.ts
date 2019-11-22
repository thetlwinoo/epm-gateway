export interface IPhotos {
  id?: number;
  thumbnailImageUrl?: string;
  originalImageUrl?: string;
  bannerTallImageUrl?: string;
  bannerWideImageUrl?: string;
  circleImageUrl?: string;
  sharpenedImageUrl?: string;
  squareImageUrl?: string;
  watermarkImageUrl?: string;
  priority?: number;
  defaultInd?: boolean;
  stockItemId?: number;
  productCategoryId?: number;
}

export class Photos implements IPhotos {
  constructor(
    public id?: number,
    public thumbnailImageUrl?: string,
    public originalImageUrl?: string,
    public bannerTallImageUrl?: string,
    public bannerWideImageUrl?: string,
    public circleImageUrl?: string,
    public sharpenedImageUrl?: string,
    public squareImageUrl?: string,
    public watermarkImageUrl?: string,
    public priority?: number,
    public defaultInd?: boolean,
    public stockItemId?: number,
    public productCategoryId?: number
  ) {
    this.defaultInd = this.defaultInd || false;
  }
}
