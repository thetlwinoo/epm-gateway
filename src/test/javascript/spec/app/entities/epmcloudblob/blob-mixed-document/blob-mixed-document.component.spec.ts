import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { EpmgatewayTestModule } from '../../../../test.module';
import { BlobMixedDocumentComponent } from 'app/entities/epmcloudblob/blob-mixed-document/blob-mixed-document.component';
import { BlobMixedDocumentService } from 'app/entities/epmcloudblob/blob-mixed-document/blob-mixed-document.service';
import { BlobMixedDocument } from 'app/shared/model/epmcloudblob/blob-mixed-document.model';

describe('Component Tests', () => {
  describe('BlobMixedDocument Management Component', () => {
    let comp: BlobMixedDocumentComponent;
    let fixture: ComponentFixture<BlobMixedDocumentComponent>;
    let service: BlobMixedDocumentService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EpmgatewayTestModule],
        declarations: [BlobMixedDocumentComponent],
        providers: []
      })
        .overrideTemplate(BlobMixedDocumentComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(BlobMixedDocumentComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(BlobMixedDocumentService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new BlobMixedDocument(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.blobMixedDocuments[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
