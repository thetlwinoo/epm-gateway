import { Moment } from 'moment';

export interface IBlobPhotos {
  id?: number;
  thumbnailPhotoBlobContentType?: string;
  thumbnailPhotoBlob?: any;
  originalPhotoBlobContentType?: string;
  originalPhotoBlob?: any;
  bannerTallPhotoBlobContentType?: string;
  bannerTallPhotoBlob?: any;
  bannerWidePhotoBlobContentType?: string;
  bannerWidePhotoBlob?: any;
  circlePhotoBlobContentType?: string;
  circlePhotoBlob?: any;
  sharpenedPhotoBlobContentType?: string;
  sharpenedPhotoBlob?: any;
  squarePhotoBlobContentType?: string;
  squarePhotoBlob?: any;
  watermarkPhotoBlobContentType?: string;
  watermarkPhotoBlob?: any;
  lastEditedBy?: string;
  lastEditedWhen?: Moment;
}

export class BlobPhotos implements IBlobPhotos {
  constructor(
    public id?: number,
    public thumbnailPhotoBlobContentType?: string,
    public thumbnailPhotoBlob?: any,
    public originalPhotoBlobContentType?: string,
    public originalPhotoBlob?: any,
    public bannerTallPhotoBlobContentType?: string,
    public bannerTallPhotoBlob?: any,
    public bannerWidePhotoBlobContentType?: string,
    public bannerWidePhotoBlob?: any,
    public circlePhotoBlobContentType?: string,
    public circlePhotoBlob?: any,
    public sharpenedPhotoBlobContentType?: string,
    public sharpenedPhotoBlob?: any,
    public squarePhotoBlobContentType?: string,
    public squarePhotoBlob?: any,
    public watermarkPhotoBlobContentType?: string,
    public watermarkPhotoBlob?: any,
    public lastEditedBy?: string,
    public lastEditedWhen?: Moment
  ) {}
}
