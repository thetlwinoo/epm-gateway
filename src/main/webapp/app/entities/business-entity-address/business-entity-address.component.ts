import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { filter, map } from 'rxjs/operators';
import { JhiEventManager } from 'ng-jhipster';

import { IBusinessEntityAddress } from 'app/shared/model/business-entity-address.model';
import { AccountService } from 'app/core/auth/account.service';
import { BusinessEntityAddressService } from './business-entity-address.service';

@Component({
  selector: 'jhi-business-entity-address',
  templateUrl: './business-entity-address.component.html'
})
export class BusinessEntityAddressComponent implements OnInit, OnDestroy {
  businessEntityAddresses: IBusinessEntityAddress[];
  currentAccount: any;
  eventSubscriber: Subscription;
  currentSearch: string;

  constructor(
    protected businessEntityAddressService: BusinessEntityAddressService,
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
      this.businessEntityAddressService
        .search({
          query: this.currentSearch
        })
        .pipe(
          filter((res: HttpResponse<IBusinessEntityAddress[]>) => res.ok),
          map((res: HttpResponse<IBusinessEntityAddress[]>) => res.body)
        )
        .subscribe((res: IBusinessEntityAddress[]) => (this.businessEntityAddresses = res));
      return;
    }
    this.businessEntityAddressService
      .query()
      .pipe(
        filter((res: HttpResponse<IBusinessEntityAddress[]>) => res.ok),
        map((res: HttpResponse<IBusinessEntityAddress[]>) => res.body)
      )
      .subscribe((res: IBusinessEntityAddress[]) => {
        this.businessEntityAddresses = res;
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
    this.registerChangeInBusinessEntityAddresses();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IBusinessEntityAddress) {
    return item.id;
  }

  registerChangeInBusinessEntityAddresses() {
    this.eventSubscriber = this.eventManager.subscribe('businessEntityAddressListModification', response => this.loadAll());
  }
}
