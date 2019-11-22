import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IProductDocument } from 'app/shared/model/product-document.model';

@Component({
  selector: 'jhi-product-document-detail',
  templateUrl: './product-document-detail.component.html'
})
export class ProductDocumentDetailComponent implements OnInit {
  productDocument: IProductDocument;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ productDocument }) => {
      this.productDocument = productDocument;
    });
  }

  previousState() {
    window.history.back();
  }
}
