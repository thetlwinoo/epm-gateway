import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';
import { ISupplierImportedDocument, SupplierImportedDocument } from 'app/shared/model/supplier-imported-document.model';
import { SupplierImportedDocumentService } from './supplier-imported-document.service';
import { IUploadTransactions } from 'app/shared/model/upload-transactions.model';
import { UploadTransactionsService } from 'app/entities/upload-transactions/upload-transactions.service';

@Component({
  selector: 'jhi-supplier-imported-document-update',
  templateUrl: './supplier-imported-document-update.component.html'
})
export class SupplierImportedDocumentUpdateComponent implements OnInit {
  isSaving: boolean;

  uploadtransactions: IUploadTransactions[];

  editForm = this.fb.group({
    id: [],
    documentUrl: [],
    documentType: [],
    lastEditedBy: [],
    lastEditedWhen: [],
    uploadTransactionId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected supplierImportedDocumentService: SupplierImportedDocumentService,
    protected uploadTransactionsService: UploadTransactionsService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ supplierImportedDocument }) => {
      this.updateForm(supplierImportedDocument);
    });
    this.uploadTransactionsService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IUploadTransactions[]>) => mayBeOk.ok),
        map((response: HttpResponse<IUploadTransactions[]>) => response.body)
      )
      .subscribe((res: IUploadTransactions[]) => (this.uploadtransactions = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(supplierImportedDocument: ISupplierImportedDocument) {
    this.editForm.patchValue({
      id: supplierImportedDocument.id,
      documentUrl: supplierImportedDocument.documentUrl,
      documentType: supplierImportedDocument.documentType,
      lastEditedBy: supplierImportedDocument.lastEditedBy,
      lastEditedWhen:
        supplierImportedDocument.lastEditedWhen != null ? supplierImportedDocument.lastEditedWhen.format(DATE_TIME_FORMAT) : null,
      uploadTransactionId: supplierImportedDocument.uploadTransactionId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const supplierImportedDocument = this.createFromForm();
    if (supplierImportedDocument.id !== undefined) {
      this.subscribeToSaveResponse(this.supplierImportedDocumentService.update(supplierImportedDocument));
    } else {
      this.subscribeToSaveResponse(this.supplierImportedDocumentService.create(supplierImportedDocument));
    }
  }

  private createFromForm(): ISupplierImportedDocument {
    return {
      ...new SupplierImportedDocument(),
      id: this.editForm.get(['id']).value,
      documentUrl: this.editForm.get(['documentUrl']).value,
      documentType: this.editForm.get(['documentType']).value,
      lastEditedBy: this.editForm.get(['lastEditedBy']).value,
      lastEditedWhen:
        this.editForm.get(['lastEditedWhen']).value != null
          ? moment(this.editForm.get(['lastEditedWhen']).value, DATE_TIME_FORMAT)
          : undefined,
      uploadTransactionId: this.editForm.get(['uploadTransactionId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISupplierImportedDocument>>) {
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

  trackUploadTransactionsById(index: number, item: IUploadTransactions) {
    return item.id;
  }
}
