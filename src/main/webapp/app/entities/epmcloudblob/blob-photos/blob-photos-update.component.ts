import { Component, OnInit, ElementRef } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService, JhiDataUtils } from 'ng-jhipster';
import { IBlobPhotos, BlobPhotos } from 'app/shared/model/epmcloudblob/blob-photos.model';
import { BlobPhotosService } from './blob-photos.service';

@Component({
  selector: 'jhi-blob-photos-update',
  templateUrl: './blob-photos-update.component.html'
})
export class BlobPhotosUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    thumbnailPhotoBlob: [],
    thumbnailPhotoBlobContentType: [],
    originalPhotoBlob: [],
    originalPhotoBlobContentType: [],
    bannerTallPhotoBlob: [],
    bannerTallPhotoBlobContentType: [],
    bannerWidePhotoBlob: [],
    bannerWidePhotoBlobContentType: [],
    circlePhotoBlob: [],
    circlePhotoBlobContentType: [],
    sharpenedPhotoBlob: [],
    sharpenedPhotoBlobContentType: [],
    squarePhotoBlob: [],
    squarePhotoBlobContentType: [],
    watermarkPhotoBlob: [],
    watermarkPhotoBlobContentType: [],
    lastEditedBy: [],
    lastEditedWhen: []
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected jhiAlertService: JhiAlertService,
    protected blobPhotosService: BlobPhotosService,
    protected elementRef: ElementRef,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ blobPhotos }) => {
      this.updateForm(blobPhotos);
    });
  }

  updateForm(blobPhotos: IBlobPhotos) {
    this.editForm.patchValue({
      id: blobPhotos.id,
      thumbnailPhotoBlob: blobPhotos.thumbnailPhotoBlob,
      thumbnailPhotoBlobContentType: blobPhotos.thumbnailPhotoBlobContentType,
      originalPhotoBlob: blobPhotos.originalPhotoBlob,
      originalPhotoBlobContentType: blobPhotos.originalPhotoBlobContentType,
      bannerTallPhotoBlob: blobPhotos.bannerTallPhotoBlob,
      bannerTallPhotoBlobContentType: blobPhotos.bannerTallPhotoBlobContentType,
      bannerWidePhotoBlob: blobPhotos.bannerWidePhotoBlob,
      bannerWidePhotoBlobContentType: blobPhotos.bannerWidePhotoBlobContentType,
      circlePhotoBlob: blobPhotos.circlePhotoBlob,
      circlePhotoBlobContentType: blobPhotos.circlePhotoBlobContentType,
      sharpenedPhotoBlob: blobPhotos.sharpenedPhotoBlob,
      sharpenedPhotoBlobContentType: blobPhotos.sharpenedPhotoBlobContentType,
      squarePhotoBlob: blobPhotos.squarePhotoBlob,
      squarePhotoBlobContentType: blobPhotos.squarePhotoBlobContentType,
      watermarkPhotoBlob: blobPhotos.watermarkPhotoBlob,
      watermarkPhotoBlobContentType: blobPhotos.watermarkPhotoBlobContentType,
      lastEditedBy: blobPhotos.lastEditedBy,
      lastEditedWhen: blobPhotos.lastEditedWhen != null ? blobPhotos.lastEditedWhen.format(DATE_TIME_FORMAT) : null
    });
  }

  byteSize(field) {
    return this.dataUtils.byteSize(field);
  }

  openFile(contentType, field) {
    return this.dataUtils.openFile(contentType, field);
  }

  setFileData(event, field: string, isImage) {
    return new Promise((resolve, reject) => {
      if (event && event.target && event.target.files && event.target.files[0]) {
        const file: File = event.target.files[0];
        if (isImage && !file.type.startsWith('image/')) {
          reject(`File was expected to be an image but was found to be ${file.type}`);
        } else {
          const filedContentType: string = field + 'ContentType';
          this.dataUtils.toBase64(file, base64Data => {
            this.editForm.patchValue({
              [field]: base64Data,
              [filedContentType]: file.type
            });
          });
        }
      } else {
        reject(`Base64 data was not set as file could not be extracted from passed parameter: ${event}`);
      }
    }).then(
      // eslint-disable-next-line no-console
      () => console.log('blob added'), // success
      this.onError
    );
  }

  clearInputImage(field: string, fieldContentType: string, idInput: string) {
    this.editForm.patchValue({
      [field]: null,
      [fieldContentType]: null
    });
    if (this.elementRef && idInput && this.elementRef.nativeElement.querySelector('#' + idInput)) {
      this.elementRef.nativeElement.querySelector('#' + idInput).value = null;
    }
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const blobPhotos = this.createFromForm();
    if (blobPhotos.id !== undefined) {
      this.subscribeToSaveResponse(this.blobPhotosService.update(blobPhotos));
    } else {
      this.subscribeToSaveResponse(this.blobPhotosService.create(blobPhotos));
    }
  }

  private createFromForm(): IBlobPhotos {
    return {
      ...new BlobPhotos(),
      id: this.editForm.get(['id']).value,
      thumbnailPhotoBlobContentType: this.editForm.get(['thumbnailPhotoBlobContentType']).value,
      thumbnailPhotoBlob: this.editForm.get(['thumbnailPhotoBlob']).value,
      originalPhotoBlobContentType: this.editForm.get(['originalPhotoBlobContentType']).value,
      originalPhotoBlob: this.editForm.get(['originalPhotoBlob']).value,
      bannerTallPhotoBlobContentType: this.editForm.get(['bannerTallPhotoBlobContentType']).value,
      bannerTallPhotoBlob: this.editForm.get(['bannerTallPhotoBlob']).value,
      bannerWidePhotoBlobContentType: this.editForm.get(['bannerWidePhotoBlobContentType']).value,
      bannerWidePhotoBlob: this.editForm.get(['bannerWidePhotoBlob']).value,
      circlePhotoBlobContentType: this.editForm.get(['circlePhotoBlobContentType']).value,
      circlePhotoBlob: this.editForm.get(['circlePhotoBlob']).value,
      sharpenedPhotoBlobContentType: this.editForm.get(['sharpenedPhotoBlobContentType']).value,
      sharpenedPhotoBlob: this.editForm.get(['sharpenedPhotoBlob']).value,
      squarePhotoBlobContentType: this.editForm.get(['squarePhotoBlobContentType']).value,
      squarePhotoBlob: this.editForm.get(['squarePhotoBlob']).value,
      watermarkPhotoBlobContentType: this.editForm.get(['watermarkPhotoBlobContentType']).value,
      watermarkPhotoBlob: this.editForm.get(['watermarkPhotoBlob']).value,
      lastEditedBy: this.editForm.get(['lastEditedBy']).value,
      lastEditedWhen:
        this.editForm.get(['lastEditedWhen']).value != null
          ? moment(this.editForm.get(['lastEditedWhen']).value, DATE_TIME_FORMAT)
          : undefined
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IBlobPhotos>>) {
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
}
