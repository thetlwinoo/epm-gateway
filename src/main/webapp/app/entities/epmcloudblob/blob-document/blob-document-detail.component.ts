import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IBlobDocument } from 'app/shared/model/epmcloudblob/blob-document.model';

@Component({
  selector: 'jhi-blob-document-detail',
  templateUrl: './blob-document-detail.component.html'
})
export class BlobDocumentDetailComponent implements OnInit {
  blobDocument: IBlobDocument;

  constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ blobDocument }) => {
      this.blobDocument = blobDocument;
    });
  }

  byteSize(field) {
    return this.dataUtils.byteSize(field);
  }

  openFile(contentType, field) {
    return this.dataUtils.openFile(contentType, field);
  }
  previousState() {
    window.history.back();
  }
}
