import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { filter, map } from 'rxjs/operators';
import { JhiEventManager } from 'ng-jhipster';

import { IPhotos } from 'app/shared/model/photos.model';
import { AccountService } from 'app/core/auth/account.service';
import { PhotosService } from './photos.service';

@Component({
  selector: 'jhi-photos',
  templateUrl: './photos.component.html'
})
export class PhotosComponent implements OnInit, OnDestroy {
  photos: IPhotos[];
  currentAccount: any;
  eventSubscriber: Subscription;
  currentSearch: string;

  constructor(
    protected photosService: PhotosService,
    protected eventManager: JhiEventManager,
    protected activatedRoute: ActivatedRoute,
    protected accountService: AccountService
  ) {
    this.currentSearch =
      this.activatedRoute.snapshot && this.activatedRoute.snapshot.queryParams['search']
        ? this.activatedRoute.snapshot.queryParams['search']
        : '';
  }

  loadAll() {
    if (this.currentSearch) {
      this.photosService
        .search({
          query: this.currentSearch
        })
        .pipe(
          filter((res: HttpResponse<IPhotos[]>) => res.ok),
          map((res: HttpResponse<IPhotos[]>) => res.body)
        )
        .subscribe((res: IPhotos[]) => (this.photos = res));
      return;
    }
    this.photosService
      .query()
      .pipe(
        filter((res: HttpResponse<IPhotos[]>) => res.ok),
        map((res: HttpResponse<IPhotos[]>) => res.body)
      )
      .subscribe((res: IPhotos[]) => {
        this.photos = res;
        this.currentSearch = '';
      });
  }

  search(query) {
    if (!query) {
      return this.clear();
    }
    this.currentSearch = query;
    this.loadAll();
  }

  clear() {
    this.currentSearch = '';
    this.loadAll();
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().subscribe(account => {
      this.currentAccount = account;
    });
    this.registerChangeInPhotos();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IPhotos) {
    return item.id;
  }

  registerChangeInPhotos() {
    this.eventSubscriber = this.eventManager.subscribe('photosListModification', response => this.loadAll());
  }
}
