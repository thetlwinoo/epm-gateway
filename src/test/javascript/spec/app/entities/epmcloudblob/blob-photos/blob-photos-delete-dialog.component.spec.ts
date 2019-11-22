import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { EpmgatewayTestModule } from '../../../../test.module';
import { BlobPhotosDeleteDialogComponent } from 'app/entities/epmcloudblob/blob-photos/blob-photos-delete-dialog.component';
import { BlobPhotosService } from 'app/entities/epmcloudblob/blob-photos/blob-photos.service';

describe('Component Tests', () => {
  describe('BlobPhotos Management Delete Component', () => {
    let comp: BlobPhotosDeleteDialogComponent;
    let fixture: ComponentFixture<BlobPhotosDeleteDialogComponent>;
    let service: BlobPhotosService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EpmgatewayTestModule],
        declarations: [BlobPhotosDeleteDialogComponent]
      })
        .overrideTemplate(BlobPhotosDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(BlobPhotosDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(BlobPhotosService);
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
