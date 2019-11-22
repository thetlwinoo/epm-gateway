import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService, JhiDataUtils } from 'ng-jhipster';
import { IBlobMixedDocument, BlobMixedDocument } from 'app/shared/model/epmcloudblob/blob-mixed-document.model';
import { BlobMixedDocumentService } from './blob-mixed-document.service';

@Component({
  selector: 'jhi-blob-mixed-document-update',
  templateUrl: './blob-mixed-document-update.component.html'
})
export class BlobMixedDocumentUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    document: [],
    lastEditedBy: [],
    lastEditedWhen: []
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected jhiAlertService: JhiAlertService,
    protected blobMixedDocumentService: BlobMixedDocumentService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ blobMixedDocument }) => {
      this.updateForm(blobMixedDocument);
    });
  }

  updateForm(blobMixedDocument: IBlobMixedDocument) {
    this.editForm.patchValue({
      id: blobMixedDocument.id,
      document: blobMixedDocument.document,
      lastEditedBy: blobMixedDocument.lastEditedBy,
      lastEditedWhen: blobMixedDocument.lastEditedWhen != null ? blobMixedDocument.lastEditedWhen.format(DATE_TIME_FORMAT) : null
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

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const blobMixedDocument = this.createFromForm();
    if (blobMixedDocument.id !== undefined) {
      this.subscribeToSaveResponse(this.blobMixedDocumentService.update(blobMixedDocument));
    } else {
      this.subscribeToSaveResponse(this.blobMixedDocumentService.create(blobMixedDocument));
    }
  }

  private createFromForm(): IBlobMixedDocument {
    return {
      ...new BlobMixedDocument(),
      id: this.editForm.get(['id']).value,
      document: this.editForm.get(['document']).value,
      lastEditedBy: this.editForm.get(['lastEditedBy']).value,
      lastEditedWhen:
        this.editForm.get(['lastEditedWhen']).value != null
          ? moment(this.editForm.get(['lastEditedWhen']).value, DATE_TIME_FORMAT)
          : undefined
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IBlobMixedDocument>>) {
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
