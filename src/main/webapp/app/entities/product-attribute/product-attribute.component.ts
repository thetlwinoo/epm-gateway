import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { filter, map } from 'rxjs/operators';
import { JhiEventManager } from 'ng-jhipster';

import { IProductAttribute } from 'app/shared/model/product-attribute.model';
import { AccountService } from 'app/core/auth/account.service';
import { ProductAttributeService } from './product-attribute.service';

@Component({
  selector: 'jhi-product-attribute',
  templateUrl: './product-attribute.component.html'
})
export class ProductAttributeComponent implements OnInit, OnDestroy {
  productAttributes: IProductAttribute[];
  currentAccount: any;
  eventSubscriber: Subscription;
  currentSearch: string;

  constructor(
    protected productAttributeService: ProductAttributeService,
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
      this.productAttributeService
        .search({
          query: this.currentSearch
        })
        .pipe(
          filter((res: HttpResponse<IProductAttribute[]>) => res.ok),
          map((res: HttpResponse<IProductAttribute[]>) => res.body)
        )
        .subscribe((res: IProductAttribute[]) => (this.productAttributes = res));
      return;
    }
    this.productAttributeService
      .query()
      .pipe(
        filter((res: HttpResponse<IProductAttribute[]>) => res.ok),
        map((res: HttpResponse<IProductAttribute[]>) => res.body)
      )
      .subscribe((res: IProductAttribute[]) => {
        this.productAttributes = res;
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
    this.registerChangeInProductAttributes();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IProductAttribute) {
    return item.id;
  }

  registerChangeInProductAttributes() {
    this.eventSubscriber = this.eventManager.subscribe('productAttributeListModification', response => this.loadAll());
  }
}
