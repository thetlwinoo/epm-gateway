import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { EpmgatewayTestModule } from '../../../../test.module';
import { BlobDocumentDetailComponent } from 'app/entities/epmcloudblob/blob-document/blob-document-detail.component';
import { BlobDocument } from 'app/shared/model/epmcloudblob/blob-document.model';

describe('Component Tests', () => {
  describe('BlobDocument Management Detail Component', () => {
    let comp: BlobDocumentDetailComponent;
    let fixture: ComponentFixture<BlobDocumentDetailComponent>;
    const route = ({ data: of({ blobDocument: new BlobDocument(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EpmgatewayTestModule],
        declarations: [BlobDocumentDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(BlobDocumentDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(BlobDocumentDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.blobDocument).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
