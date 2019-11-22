import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPhotos } from 'app/shared/model/photos.model';

@Component({
  selector: 'jhi-photos-detail',
  templateUrl: './photos-detail.component.html'
})
export class PhotosDetailComponent implements OnInit {
  photos: IPhotos;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ photos }) => {
      this.photos = photos;
    });
  }

  previousState() {
    window.history.back();
  }
}
