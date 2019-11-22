import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiDataUtils } from 'ng-jhipster';

import { IBlobDocument } from 'app/shared/model/epmcloudblob/blob-document.model';
import { AccountService } from 'app/core/auth/account.service';
import { BlobDocumentService } from './blob-document.service';

@Component({
  selector: 'jhi-blob-document',
  templateUrl: './blob-document.component.html'
})
export class BlobDocumentComponent implements OnInit, OnDestroy {
  blobDocuments: IBlobDocument[];
  currentAccount: any;
  eventSubscriber: Subscription;
  currentSearch: string;

  constructor(
    protected blobDocumentService: BlobDocumentService,
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
      this.blobDocumentService
        .search({
          query: this.currentSearch
        })
        .pipe(
          filter((res: HttpResponse<IBlobDocument[]>) => res.ok),
          map((res: HttpResponse<IBlobDocument[]>) => res.body)
        )
        .subscribe((res: IBlobDocument[]) => (this.blobDocuments = res));
      return;
    }
    this.blobDocumentService
      .query()
      .pipe(
        filter((res: HttpResponse<IBlobDocument[]>) => res.ok),
        map((res: HttpResponse<IBlobDocument[]>) => res.body)
      )
      .subscribe((res: IBlobDocument[]) => {
        this.blobDocuments = res;
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
    this.registerChangeInBlobDocuments();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IBlobDocument) {
    return item.id;
  }

  byteSize(field) {
    return this.dataUtils.byteSize(field);
  }

  openFile(contentType, field) {
    return this.dataUtils.openFile(contentType, field);
  }

  registerChangeInBlobDocuments() {
    this.eventSubscriber = this.eventManager.subscribe('blobDocumentListModification', response => this.loadAll());
  }
}
