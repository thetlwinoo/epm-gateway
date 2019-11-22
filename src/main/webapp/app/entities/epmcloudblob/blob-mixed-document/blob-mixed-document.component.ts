import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiDataUtils } from 'ng-jhipster';

import { IBlobMixedDocument } from 'app/shared/model/epmcloudblob/blob-mixed-document.model';
import { AccountService } from 'app/core/auth/account.service';
import { BlobMixedDocumentService } from './blob-mixed-document.service';

@Component({
  selector: 'jhi-blob-mixed-document',
  templateUrl: './blob-mixed-document.component.html'
})
export class BlobMixedDocumentComponent implements OnInit, OnDestroy {
  blobMixedDocuments: IBlobMixedDocument[];
  currentAccount: any;
  eventSubscriber: Subscription;
  currentSearch: string;

  constructor(
    protected blobMixedDocumentService: BlobMixedDocumentService,
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
      this.blobMixedDocumentService
        .search({
          query: this.currentSearch
        })
        .pipe(
          filter((res: HttpResponse<IBlobMixedDocument[]>) => res.ok),
          map((res: HttpResponse<IBlobMixedDocument[]>) => res.body)
        )
        .subscribe((res: IBlobMixedDocument[]) => (this.blobMixedDocuments = res));
      return;
    }
    this.blobMixedDocumentService
      .query()
      .pipe(
        filter((res: HttpResponse<IBlobMixedDocument[]>) => res.ok),
        map((res: HttpResponse<IBlobMixedDocument[]>) => res.body)
      )
      .subscribe((res: IBlobMixedDocument[]) => {
        this.blobMixedDocuments = res;
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
    this.registerChangeInBlobMixedDocuments();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IBlobMixedDocument) {
    return item.id;
  }

  byteSize(field) {
    return this.dataUtils.byteSize(field);
  }

  openFile(contentType, field) {
    return this.dataUtils.openFile(contentType, field);
  }

  registerChangeInBlobMixedDocuments() {
    this.eventSubscriber = this.eventManager.subscribe('blobMixedDocumentListModification', response => this.loadAll());
  }
}
