import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { filter, map } from 'rxjs/operators';
import { JhiEventManager } from 'ng-jhipster';

import { IProductDocument } from 'app/shared/model/product-document.model';
import { AccountService } from 'app/core/auth/account.service';
import { ProductDocumentService } from './product-document.service';

@Component({
  selector: 'jhi-product-document',
  templateUrl: './product-document.component.html'
})
export class ProductDocumentComponent implements OnInit, OnDestroy {
  productDocuments: IProductDocument[];
  currentAccount: any;
  eventSubscriber: Subscription;
  currentSearch: string;

  constructor(
    protected productDocumentService: ProductDocumentService,
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
      this.productDocumentService
        .search({
          query: this.currentSearch
        })
        .pipe(
          filter((res: HttpResponse<IProductDocument[]>) => res.ok),
          map((res: HttpResponse<IProductDocument[]>) => res.body)
        )
        .subscribe((res: IProductDocument[]) => (this.productDocuments = res));
      return;
    }
    this.productDocumentService
      .query()
      .pipe(
        filter((res: HttpResponse<IProductDocument[]>) => res.ok),
        map((res: HttpResponse<IProductDocument[]>) => res.body)
      )
      .subscribe((res: IProductDocument[]) => {
        this.productDocuments = res;
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
    this.registerChangeInProductDocuments();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IProductDocument) {
    return item.id;
  }

  registerChangeInProductDocuments() {
    this.eventSubscriber = this.eventManager.subscribe('productDocumentListModification', response => this.loadAll());
  }
}
