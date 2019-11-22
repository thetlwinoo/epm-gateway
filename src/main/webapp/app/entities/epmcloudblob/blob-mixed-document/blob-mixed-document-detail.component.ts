import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IBlobMixedDocument } from 'app/shared/model/epmcloudblob/blob-mixed-document.model';

@Component({
  selector: 'jhi-blob-mixed-document-detail',
  templateUrl: './blob-mixed-document-detail.component.html'
})
export class BlobMixedDocumentDetailComponent implements OnInit {
  blobMixedDocument: IBlobMixedDocument;

  constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ blobMixedDocument }) => {
      this.blobMixedDocument = blobMixedDocument;
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
