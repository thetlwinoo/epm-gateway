import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IBlobPhotos } from 'app/shared/model/epmcloudblob/blob-photos.model';

@Component({
  selector: 'jhi-blob-photos-detail',
  templateUrl: './blob-photos-detail.component.html'
})
export class BlobPhotosDetailComponent implements OnInit {
  blobPhotos: IBlobPhotos;

  constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ blobPhotos }) => {
      this.blobPhotos = blobPhotos;
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
