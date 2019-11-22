import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IBlobPhotos } from 'app/shared/model/epmcloudblob/blob-photos.model';
import { BlobPhotosService } from './blob-photos.service';

@Component({
  selector: 'jhi-blob-photos-delete-dialog',
  templateUrl: './blob-photos-delete-dialog.component.html'
})
export class BlobPhotosDeleteDialogComponent {
  blobPhotos: IBlobPhotos;

  constructor(
    protected blobPhotosService: BlobPhotosService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.blobPhotosService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'blobPhotosListModification',
        content: 'Deleted an blobPhotos'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-blob-photos-delete-popup',
  template: ''
})
export class BlobPhotosDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ blobPhotos }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(BlobPhotosDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.blobPhotos = blobPhotos;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/blob-photos', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/blob-photos', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          }
        );
      }, 0);
    });
  }

  ngOnDestroy() {
    this.ngbModalRef = null;
  }
}
