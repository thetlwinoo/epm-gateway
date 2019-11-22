import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { EpmgatewayTestModule } from '../../../../test.module';
import { BlobPhotosUpdateComponent } from 'app/entities/epmcloudblob/blob-photos/blob-photos-update.component';
import { BlobPhotosService } from 'app/entities/epmcloudblob/blob-photos/blob-photos.service';
import { BlobPhotos } from 'app/shared/model/epmcloudblob/blob-photos.model';

describe('Component Tests', () => {
  describe('BlobPhotos Management Update Component', () => {
    let comp: BlobPhotosUpdateComponent;
    let fixture: ComponentFixture<BlobPhotosUpdateComponent>;
    let service: BlobPhotosService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EpmgatewayTestModule],
        declarations: [BlobPhotosUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(BlobPhotosUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(BlobPhotosUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(BlobPhotosService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new BlobPhotos(123);
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
        const entity = new BlobPhotos();
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
