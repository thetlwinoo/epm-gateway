import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IPhotos, Photos } from 'app/shared/model/photos.model';
import { PhotosService } from './photos.service';
import { IStockItems } from 'app/shared/model/stock-items.model';
import { StockItemsService } from 'app/entities/stock-items/stock-items.service';
import { IProductCategory } from 'app/shared/model/product-category.model';
import { ProductCategoryService } from 'app/entities/product-category/product-category.service';

@Component({
  selector: 'jhi-photos-update',
  templateUrl: './photos-update.component.html'
})
export class PhotosUpdateComponent implements OnInit {
  isSaving: boolean;

  stockitems: IStockItems[];

  productcategories: IProductCategory[];

  editForm = this.fb.group({
    id: [],
    thumbnailImageUrl: [],
    originalImageUrl: [],
    bannerTallImageUrl: [],
    bannerWideImageUrl: [],
    circleImageUrl: [],
    sharpenedImageUrl: [],
    squareImageUrl: [],
    watermarkImageUrl: [],
    priority: [],
    defaultInd: [],
    stockItemId: [],
    productCategoryId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected photosService: PhotosService,
    protected stockItemsService: StockItemsService,
    protected productCategoryService: ProductCategoryService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ photos }) => {
      this.updateForm(photos);
    });
    this.stockItemsService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IStockItems[]>) => mayBeOk.ok),
        map((response: HttpResponse<IStockItems[]>) => response.body)
      )
      .subscribe((res: IStockItems[]) => (this.stockitems = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.productCategoryService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IProductCategory[]>) => mayBeOk.ok),
        map((response: HttpResponse<IProductCategory[]>) => response.body)
      )
      .subscribe((res: IProductCategory[]) => (this.productcategories = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(photos: IPhotos) {
    this.editForm.patchValue({
      id: photos.id,
      thumbnailImageUrl: photos.thumbnailImageUrl,
      originalImageUrl: photos.originalImageUrl,
      bannerTallImageUrl: photos.bannerTallImageUrl,
      bannerWideImageUrl: photos.bannerWideImageUrl,
      circleImageUrl: photos.circleImageUrl,
      sharpenedImageUrl: photos.sharpenedImageUrl,
      squareImageUrl: photos.squareImageUrl,
      watermarkImageUrl: photos.watermarkImageUrl,
      priority: photos.priority,
      defaultInd: photos.defaultInd,
      stockItemId: photos.stockItemId,
      productCategoryId: photos.productCategoryId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const photos = this.createFromForm();
    if (photos.id !== undefined) {
      this.subscribeToSaveResponse(this.photosService.update(photos));
    } else {
      this.subscribeToSaveResponse(this.photosService.create(photos));
    }
  }

  private createFromForm(): IPhotos {
    return {
      ...new Photos(),
      id: this.editForm.get(['id']).value,
      thumbnailImageUrl: this.editForm.get(['thumbnailImageUrl']).value,
      originalImageUrl: this.editForm.get(['originalImageUrl']).value,
      bannerTallImageUrl: this.editForm.get(['bannerTallImageUrl']).value,
      bannerWideImageUrl: this.editForm.get(['bannerWideImageUrl']).value,
      circleImageUrl: this.editForm.get(['circleImageUrl']).value,
      sharpenedImageUrl: this.editForm.get(['sharpenedImageUrl']).value,
      squareImageUrl: this.editForm.get(['squareImageUrl']).value,
      watermarkImageUrl: this.editForm.get(['watermarkImageUrl']).value,
      priority: this.editForm.get(['priority']).value,
      defaultInd: this.editForm.get(['defaultInd']).value,
      stockItemId: this.editForm.get(['stockItemId']).value,
      productCategoryId: this.editForm.get(['productCategoryId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPhotos>>) {
    result.subscribe(() => this.onSaveSuccess(), () => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }

  trackStockItemsById(index: number, item: IStockItems) {
    return item.id;
  }

  trackProductCategoryById(index: number, item: IProductCategory) {
    return item.id;
  }
}
