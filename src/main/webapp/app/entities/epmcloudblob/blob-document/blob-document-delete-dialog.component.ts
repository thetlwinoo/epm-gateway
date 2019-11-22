import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IBlobDocument } from 'app/shared/model/epmcloudblob/blob-document.model';
import { BlobDocumentService } from './blob-document.service';

@Component({
  selector: 'jhi-blob-document-delete-dialog',
  templateUrl: './blob-document-delete-dialog.component.html'
})
export class BlobDocumentDeleteDialogComponent {
  blobDocument: IBlobDocument;

  constructor(
    protected blobDocumentService: BlobDocumentService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.blobDocumentService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'blobDocumentListModification',
        content: 'Deleted an blobDocument'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-blob-document-delete-popup',
  template: ''
})
export class BlobDocumentDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ blobDocument }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(BlobDocumentDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.blobDocument = blobDocument;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/blob-document', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/blob-document', { outlets: { popup: null } }]);
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
