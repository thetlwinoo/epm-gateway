import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { filter, map } from 'rxjs/operators';
import { JhiEventManager } from 'ng-jhipster';

import { ISuppliers } from 'app/shared/model/suppliers.model';
import { AccountService } from 'app/core/auth/account.service';
import { SuppliersService } from './suppliers.service';

@Component({
  selector: 'jhi-suppliers',
  templateUrl: './suppliers.component.html'
})
export class SuppliersComponent implements OnInit, OnDestroy {
  suppliers: ISuppliers[];
  currentAccount: any;
  eventSubscriber: Subscription;
  currentSearch: string;

  constructor(
    protected suppliersService: SuppliersService,
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
      this.suppliersService
        .search({
          query: this.currentSearch
        })
        .pipe(
          filter((res: HttpResponse<ISuppliers[]>) => res.ok),
          map((res: HttpResponse<ISuppliers[]>) => res.body)
        )
        .subscribe((res: ISuppliers[]) => (this.suppliers = res));
      return;
    }
    this.suppliersService
      .query()
      .pipe(
        filter((res: HttpResponse<ISuppliers[]>) => res.ok),
        map((res: HttpResponse<ISuppliers[]>) => res.body)
      )
      .subscribe((res: ISuppliers[]) => {
        this.suppliers = res;
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
    this.registerChangeInSuppliers();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: ISuppliers) {
    return item.id;
  }

  registerChangeInSuppliers() {
    this.eventSubscriber = this.eventManager.subscribe('suppliersListModification', response => this.loadAll());
  }
}
