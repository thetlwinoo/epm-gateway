import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { filter, map } from 'rxjs/operators';
import { JhiEventManager } from 'ng-jhipster';

import { IAddresses } from 'app/shared/model/addresses.model';
import { AccountService } from 'app/core/auth/account.service';
import { AddressesService } from './addresses.service';

@Component({
  selector: 'jhi-addresses',
  templateUrl: './addresses.component.html'
})
export class AddressesComponent implements OnInit, OnDestroy {
  addresses: IAddresses[];
  currentAccount: any;
  eventSubscriber: Subscription;
  currentSearch: string;

  constructor(
    protected addressesService: AddressesService,
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
      this.addressesService
        .search({
          query: this.currentSearch
        })
        .pipe(
          filter((res: HttpResponse<IAddresses[]>) => res.ok),
          map((res: HttpResponse<IAddresses[]>) => res.body)
        )
        .subscribe((res: IAddresses[]) => (this.addresses = res));
      return;
    }
    this.addressesService
      .query()
      .pipe(
        filter((res: HttpResponse<IAddresses[]>) => res.ok),
        map((res: HttpResponse<IAddresses[]>) => res.body)
      )
      .subscribe((res: IAddresses[]) => {
        this.addresses = res;
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
    this.registerChangeInAddresses();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IAddresses) {
    return item.id;
  }

  registerChangeInAddresses() {
    this.eventSubscriber = this.eventManager.subscribe('addressesListModification', response => this.loadAll());
  }
}
