import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { filter, map } from 'rxjs/operators';
import { JhiEventManager } from 'ng-jhipster';

import { IProductTags } from 'app/shared/model/product-tags.model';
import { AccountService } from 'app/core/auth/account.service';
import { ProductTagsService } from './product-tags.service';

@Component({
  selector: 'jhi-product-tags',
  templateUrl: './product-tags.component.html'
})
export class ProductTagsComponent implements OnInit, OnDestroy {
  productTags: IProductTags[];
  currentAccount: any;
  eventSubscriber: Subscription;
  currentSearch: string;

  constructor(
    protected productTagsService: ProductTagsService,
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
      this.productTagsService
        .search({
          query: this.currentSearch
        })
        .pipe(
          filter((res: HttpResponse<IProductTags[]>) => res.ok),
          map((res: HttpResponse<IProductTags[]>) => res.body)
        )
        .subscribe((res: IProductTags[]) => (this.productTags = res));
      return;
    }
    this.productTagsService
      .query()
      .pipe(
        filter((res: HttpResponse<IProductTags[]>) => res.ok),
        map((res: HttpResponse<IProductTags[]>) => res.body)
      )
      .subscribe((res: IProductTags[]) => {
        this.productTags = res;
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
    this.registerChangeInProductTags();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IProductTags) {
    return item.id;
  }

  registerChangeInProductTags() {
    this.eventSubscriber = this.eventManager.subscribe('productTagsListModification', response => this.loadAll());
  }
}
