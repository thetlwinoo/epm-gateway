import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { EpmgatewayTestModule } from '../../../../test.module';
import { BlobMixedDocumentUpdateComponent } from 'app/entities/epmcloudblob/blob-mixed-document/blob-mixed-document-update.component';
import { BlobMixedDocumentService } from 'app/entities/epmcloudblob/blob-mixed-document/blob-mixed-document.service';
import { BlobMixedDocument } from 'app/shared/model/epmcloudblob/blob-mixed-document.model';

describe('Component Tests', () => {
  describe('BlobMixedDocument Management Update Component', () => {
    let comp: BlobMixedDocumentUpdateComponent;
    let fixture: ComponentFixture<BlobMixedDocumentUpdateComponent>;
    let service: BlobMixedDocumentService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EpmgatewayTestModule],
        declarations: [BlobMixedDocumentUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(BlobMixedDocumentUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(BlobMixedDocumentUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(BlobMixedDocumentService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new BlobMixedDocument(123);
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
        const entity = new BlobMixedDocument();
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
