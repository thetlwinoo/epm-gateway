import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiDataUtils } from 'ng-jhipster';

import { IBlobPhotos } from 'app/shared/model/epmcloudblob/blob-photos.model';
import { AccountService } from 'app/core/auth/account.service';
import { BlobPhotosService } from './blob-photos.service';

@Component({
  selector: 'jhi-blob-photos',
  templateUrl: './blob-photos.component.html'
})
export class BlobPhotosComponent implements OnInit, OnDestroy {
  blobPhotos: IBlobPhotos[];
  currentAccount: any;
  eventSubscriber: Subscription;
  currentSearch: string;

  constructor(
    protected blobPhotosService: BlobPhotosService,
    protected dataUtils: JhiDataUtils,
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
      this.blobPhotosService
        .search({
          query: this.currentSearch
        })
        .pipe(
          filter((res: HttpResponse<IBlobPhotos[]>) => res.ok),
          map((res: HttpResponse<IBlobPhotos[]>) => res.body)
        )
        .subscribe((res: IBlobPhotos[]) => (this.blobPhotos = res));
      return;
    }
    this.blobPhotosService
      .query()
      .pipe(
        filter((res: HttpResponse<IBlobPhotos[]>) => res.ok),
        map((res: HttpResponse<IBlobPhotos[]>) => res.body)
      )
      .subscribe((res: IBlobPhotos[]) => {
        this.blobPhotos = res;
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
    this.registerChangeInBlobPhotos();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IBlobPhotos) {
    return item.id;
  }

  byteSize(field) {
    return this.dataUtils.byteSize(field);
  }

  openFile(contentType, field) {
    return this.dataUtils.openFile(contentType, field);
  }

  registerChangeInBlobPhotos() {
    this.eventSubscriber = this.eventManager.subscribe('blobPhotosListModification', response => this.loadAll());
  }
}
