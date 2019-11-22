import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IBlobMixedDocument } from 'app/shared/model/epmcloudblob/blob-mixed-document.model';
import { BlobMixedDocumentService } from './blob-mixed-document.service';

@Component({
  selector: 'jhi-blob-mixed-document-delete-dialog',
  templateUrl: './blob-mixed-document-delete-dialog.component.html'
})
export class BlobMixedDocumentDeleteDialogComponent {
  blobMixedDocument: IBlobMixedDocument;

  constructor(
    protected blobMixedDocumentService: BlobMixedDocumentService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.blobMixedDocumentService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'blobMixedDocumentListModification',
        content: 'Deleted an blobMixedDocument'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-blob-mixed-document-delete-popup',
  template: ''
})
export class BlobMixedDocumentDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ blobMixedDocument }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(BlobMixedDocumentDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.blobMixedDocument = blobMixedDocument;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/blob-mixed-document', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/blob-mixed-document', { outlets: { popup: null } }]);
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
