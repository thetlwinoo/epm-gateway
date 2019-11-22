import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { filter, map } from 'rxjs/operators';
import { JhiEventManager } from 'ng-jhipster';

import { IPackageTypes } from 'app/shared/model/package-types.model';
import { AccountService } from 'app/core/auth/account.service';
import { PackageTypesService } from './package-types.service';

@Component({
  selector: 'jhi-package-types',
  templateUrl: './package-types.component.html'
})
export class PackageTypesComponent implements OnInit, OnDestroy {
  packageTypes: IPackageTypes[];
  currentAccount: any;
  eventSubscriber: Subscription;
  currentSearch: string;

  constructor(
    protected packageTypesService: PackageTypesService,
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
      this.packageTypesService
        .search({
          query: this.currentSearch
        })
        .pipe(
          filter((res: HttpResponse<IPackageTypes[]>) => res.ok),
          map((res: HttpResponse<IPackageTypes[]>) => res.body)
        )
        .subscribe((res: IPackageTypes[]) => (this.packageTypes = res));
      return;
    }
    this.packageTypesService
      .query()
      .pipe(
        filter((res: HttpResponse<IPackageTypes[]>) => res.ok),
        map((res: HttpResponse<IPackageTypes[]>) => res.body)
      )
      .subscribe((res: IPackageTypes[]) => {
        this.packageTypes = res;
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
    this.registerChangeInPackageTypes();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IPackageTypes) {
    return item.id;
  }

  registerChangeInPackageTypes() {
    this.eventSubscriber = this.eventManager.subscribe('packageTypesListModification', response => this.loadAll());
  }
}
