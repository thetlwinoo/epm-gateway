import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { EpmgatewayTestModule } from '../../../../test.module';
import { BlobPhotosComponent } from 'app/entities/epmcloudblob/blob-photos/blob-photos.component';
import { BlobPhotosService } from 'app/entities/epmcloudblob/blob-photos/blob-photos.service';
import { BlobPhotos } from 'app/shared/model/epmcloudblob/blob-photos.model';

describe('Component Tests', () => {
  describe('BlobPhotos Management Component', () => {
    let comp: BlobPhotosComponent;
    let fixture: ComponentFixture<BlobPhotosComponent>;
    let service: BlobPhotosService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EpmgatewayTestModule],
        declarations: [BlobPhotosComponent],
        providers: []
      })
        .overrideTemplate(BlobPhotosComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(BlobPhotosComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(BlobPhotosService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new BlobPhotos(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.blobPhotos[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
