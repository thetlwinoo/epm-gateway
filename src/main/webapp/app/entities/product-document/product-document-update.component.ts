import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IProductDocument, ProductDocument } from 'app/shared/model/product-document.model';
import { ProductDocumentService } from './product-document.service';
import { IWarrantyTypes } from 'app/shared/model/warranty-types.model';
import { WarrantyTypesService } from 'app/entities/warranty-types/warranty-types.service';
import { ICulture } from 'app/shared/model/culture.model';
import { CultureService } from 'app/entities/culture/culture.service';

@Component({
  selector: 'jhi-product-document-update',
  templateUrl: './product-document-update.component.html'
})
export class ProductDocumentUpdateComponent implements OnInit {
  isSaving: boolean;

  warrantytypes: IWarrantyTypes[];

  cultures: ICulture[];

  editForm = this.fb.group({
    id: [],
    videoUrl: [],
    highlightsUrl: [],
    longDescriptionUrl: [],
    shortDescriptionUrl: [],
    descriptionUrl: [],
    careInstructionsUrl: [],
    specialFeaturesUrl: [],
    usageAndSideEffectsUrl: [],
    safetyWarnningUrl: [],
    productType: [],
    modelName: [],
    modelNumber: [],
    fabricType: [],
    productComplianceCertificate: [],
    genuineAndLegal: [],
    countryOfOrigin: [],
    warrantyPeriod: [],
    warrantyPolicy: [],
    warrantyTypeId: [],
    cultureId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected productDocumentService: ProductDocumentService,
    protected warrantyTypesService: WarrantyTypesService,
    protected cultureService: CultureService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ productDocument }) => {
      this.updateForm(productDocument);
    });
    this.warrantyTypesService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IWarrantyTypes[]>) => mayBeOk.ok),
        map((response: HttpResponse<IWarrantyTypes[]>) => response.body)
      )
      .subscribe((res: IWarrantyTypes[]) => (this.warrantytypes = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.cultureService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<ICulture[]>) => mayBeOk.ok),
        map((response: HttpResponse<ICulture[]>) => response.body)
      )
      .subscribe((res: ICulture[]) => (this.cultures = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(productDocument: IProductDocument) {
    this.editForm.patchValue({
      id: productDocument.id,
      videoUrl: productDocument.videoUrl,
      highlightsUrl: productDocument.highlightsUrl,
      longDescriptionUrl: productDocument.longDescriptionUrl,
      shortDescriptionUrl: productDocument.shortDescriptionUrl,
      descriptionUrl: productDocument.descriptionUrl,
      careInstructionsUrl: productDocument.careInstructionsUrl,
      specialFeaturesUrl: productDocument.specialFeaturesUrl,
      usageAndSideEffectsUrl: productDocument.usageAndSideEffectsUrl,
      safetyWarnningUrl: productDocument.safetyWarnningUrl,
      productType: productDocument.productType,
      modelName: productDocument.modelName,
      modelNumber: productDocument.modelNumber,
      fabricType: productDocument.fabricType,
      productComplianceCertificate: productDocument.productComplianceCertificate,
      genuineAndLegal: productDocument.genuineAndLegal,
      countryOfOrigin: productDocument.countryOfOrigin,
      warrantyPeriod: productDocument.warrantyPeriod,
      warrantyPolicy: productDocument.warrantyPolicy,
      warrantyTypeId: productDocument.warrantyTypeId,
      cultureId: productDocument.cultureId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const productDocument = this.createFromForm();
    if (productDocument.id !== undefined) {
      this.subscribeToSaveResponse(this.productDocumentService.update(productDocument));
    } else {
      this.subscribeToSaveResponse(this.productDocumentService.create(productDocument));
    }
  }

  private createFromForm(): IProductDocument {
    return {
      ...new ProductDocument(),
      id: this.editForm.get(['id']).value,
      videoUrl: this.editForm.get(['videoUrl']).value,
      highlightsUrl: this.editForm.get(['highlightsUrl']).value,
      longDescriptionUrl: this.editForm.get(['longDescriptionUrl']).value,
      shortDescriptionUrl: this.editForm.get(['shortDescriptionUrl']).value,
      descriptionUrl: this.editForm.get(['descriptionUrl']).value,
      careInstructionsUrl: this.editForm.get(['careInstructionsUrl']).value,
      specialFeaturesUrl: this.editForm.get(['specialFeaturesUrl']).value,
      usageAndSideEffectsUrl: this.editForm.get(['usageAndSideEffectsUrl']).value,
      safetyWarnningUrl: this.editForm.get(['safetyWarnningUrl']).value,
      productType: this.editForm.get(['productType']).value,
      modelName: this.editForm.get(['modelName']).value,
      modelNumber: this.editForm.get(['modelNumber']).value,
      fabricType: this.editForm.get(['fabricType']).value,
      productComplianceCertificate: this.editForm.get(['productComplianceCertificate']).value,
      genuineAndLegal: this.editForm.get(['genuineAndLegal']).value,
      countryOfOrigin: this.editForm.get(['countryOfOrigin']).value,
      warrantyPeriod: this.editForm.get(['warrantyPeriod']).value,
      warrantyPolicy: this.editForm.get(['warrantyPolicy']).value,
      warrantyTypeId: this.editForm.get(['warrantyTypeId']).value,
      cultureId: this.editForm.get(['cultureId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IProductDocument>>) {
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

  trackWarrantyTypesById(index: number, item: IWarrantyTypes) {
    return item.id;
  }

  trackCultureById(index: number, item: ICulture) {
    return item.id;
  }
}
