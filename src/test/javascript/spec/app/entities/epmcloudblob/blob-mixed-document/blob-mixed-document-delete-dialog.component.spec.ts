import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { EpmgatewayTestModule } from '../../../../test.module';
import { BlobMixedDocumentDeleteDialogComponent } from 'app/entities/epmcloudblob/blob-mixed-document/blob-mixed-document-delete-dialog.component';
import { BlobMixedDocumentService } from 'app/entities/epmcloudblob/blob-mixed-document/blob-mixed-document.service';

describe('Component Tests', () => {
  describe('BlobMixedDocument Management Delete Component', () => {
    let comp: BlobMixedDocumentDeleteDialogComponent;
    let fixture: ComponentFixture<BlobMixedDocumentDeleteDialogComponent>;
    let service: BlobMixedDocumentService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EpmgatewayTestModule],
        declarations: [BlobMixedDocumentDeleteDialogComponent]
      })
        .overrideTemplate(BlobMixedDocumentDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(BlobMixedDocumentDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(BlobMixedDocumentService);
      mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
      mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
    });

    describe('confirmDelete', () => {
      it('Should call delete service on confirmDelete', inject(
        [],
        fakeAsync(() => {
          // GIVEN
          spyOn(service, 'delete').and.returnValue(of({}));

          // WHEN
          comp.confirmDelete(123);
          tick();

          // THEN
          expect(service.delete).toHaveBeenCalledWith(123);
          expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
          expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
        })
      ));
    });
  });
});
