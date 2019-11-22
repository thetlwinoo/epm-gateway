import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { EpmgatewayTestModule } from '../../../../test.module';
import { BlobPhotosDetailComponent } from 'app/entities/epmcloudblob/blob-photos/blob-photos-detail.component';
import { BlobPhotos } from 'app/shared/model/epmcloudblob/blob-photos.model';

describe('Component Tests', () => {
  describe('BlobPhotos Management Detail Component', () => {
    let comp: BlobPhotosDetailComponent;
    let fixture: ComponentFixture<BlobPhotosDetailComponent>;
    const route = ({ data: of({ blobPhotos: new BlobPhotos(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EpmgatewayTestModule],
        declarations: [BlobPhotosDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(BlobPhotosDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(BlobPhotosDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.blobPhotos).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
