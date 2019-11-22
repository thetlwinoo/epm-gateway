import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ISupplierImportedDocument } from 'app/shared/model/supplier-imported-document.model';

@Component({
  selector: 'jhi-supplier-imported-document-detail',
  templateUrl: './supplier-imported-document-detail.component.html'
})
export class SupplierImportedDocumentDetailComponent implements OnInit {
  supplierImportedDocument: ISupplierImportedDocument;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ supplierImportedDocument }) => {
      this.supplierImportedDocument = supplierImportedDocument;
    });
  }

  previousState() {
    window.history.back();
  }
}
