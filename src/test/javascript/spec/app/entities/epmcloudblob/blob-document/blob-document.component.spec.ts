import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { EpmgatewayTestModule } from '../../../../test.module';
import { BlobDocumentComponent } from 'app/entities/epmcloudblob/blob-document/blob-document.component';
import { BlobDocumentService } from 'app/entities/epmcloudblob/blob-document/blob-document.service';
import { BlobDocument } from 'app/shared/model/epmcloudblob/blob-document.model';

describe('Component Tests', () => {
  describe('BlobDocument Management Component', () => {
    let comp: BlobDocumentComponent;
    let fixture: ComponentFixture<BlobDocumentComponent>;
    let service: BlobDocumentService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EpmgatewayTestModule],
        declarations: [BlobDocumentComponent],
        providers: []
      })
        .overrideTemplate(BlobDocumentComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(BlobDocumentComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(BlobDocumentService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new BlobDocument(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.blobDocuments[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
