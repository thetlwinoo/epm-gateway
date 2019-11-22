import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { EpmgatewayTestModule } from '../../../test.module';
import { CustomerCategoriesDeleteDialogComponent } from 'app/entities/customer-categories/customer-categories-delete-dialog.component';
import { CustomerCategoriesService } from 'app/entities/customer-categories/customer-categories.service';

describe('Component Tests', () => {
  describe('CustomerCategories Management Delete Component', () => {
    let comp: CustomerCategoriesDeleteDialogComponent;
    let fixture: ComponentFixture<CustomerCategoriesDeleteDialogComponent>;
    let service: CustomerCategoriesService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EpmgatewayTestModule],
        declarations: [CustomerCategoriesDeleteDialogComponent]
      })
        .overrideTemplate(CustomerCategoriesDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CustomerCategoriesDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CustomerCategoriesService);
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