import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { EpmgatewayTestModule } from '../../../../test.module';
import { BlobDocumentUpdateComponent } from 'app/entities/epmcloudblob/blob-document/blob-document-update.component';
import { BlobDocumentService } from 'app/entities/epmcloudblob/blob-document/blob-document.service';
import { BlobDocument } from 'app/shared/model/epmcloudblob/blob-document.model';

describe('Component Tests', () => {
  describe('BlobDocument Management Update Component', () => {
    let comp: BlobDocumentUpdateComponent;
    let fixture: ComponentFixture<BlobDocumentUpdateComponent>;
    let service: BlobDocumentService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EpmgatewayTestModule],
        declarations: [BlobDocumentUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(BlobDocumentUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(BlobDocumentUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(BlobDocumentService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new BlobDocument(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new BlobDocument();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
