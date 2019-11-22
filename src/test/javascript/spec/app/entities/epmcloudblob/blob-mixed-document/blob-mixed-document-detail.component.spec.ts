import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { EpmgatewayTestModule } from '../../../../test.module';
import { BlobMixedDocumentDetailComponent } from 'app/entities/epmcloudblob/blob-mixed-document/blob-mixed-document-detail.component';
import { BlobMixedDocument } from 'app/shared/model/epmcloudblob/blob-mixed-document.model';

describe('Component Tests', () => {
  describe('BlobMixedDocument Management Detail Component', () => {
    let comp: BlobMixedDocumentDetailComponent;
    let fixture: ComponentFixture<BlobMixedDocumentDetailComponent>;
    const route = ({ data: of({ blobMixedDocument: new BlobMixedDocument(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EpmgatewayTestModule],
        declarations: [BlobMixedDocumentDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(BlobMixedDocumentDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(BlobMixedDocumentDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.blobMixedDocument).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
