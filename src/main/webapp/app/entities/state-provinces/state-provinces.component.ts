import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { filter, map } from 'rxjs/operators';
import { JhiEventManager } from 'ng-jhipster';

import { IStateProvinces } from 'app/shared/model/state-provinces.model';
import { AccountService } from 'app/core/auth/account.service';
import { StateProvincesService } from './state-provinces.service';

@Component({
  selector: 'jhi-state-provinces',
  templateUrl: './state-provinces.component.html'
})
export class StateProvincesComponent implements OnInit, OnDestroy {
  stateProvinces: IStateProvinces[];
  currentAccount: any;
  eventSubscriber: Subscription;
  currentSearch: string;

  constructor(
    protected stateProvincesService: StateProvincesService,
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
      this.stateProvincesService
        .search({
          query: this.currentSearch
        })
        .pipe(
          filter((res: HttpResponse<IStateProvinces[]>) => res.ok),
          map((res: HttpResponse<IStateProvinces[]>) => res.body)
        )
        .subscribe((res: IStateProvinces[]) => (this.stateProvinces = res));
      return;
    }
    this.stateProvincesService
      .query()
      .pipe(
        filter((res: HttpResponse<IStateProvinces[]>) => res.ok),
        map((res: HttpResponse<IStateProvinces[]>) => res.body)
      )
      .subscribe((res: IStateProvinces[]) => {
        this.stateProvinces = res;
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
    this.registerChangeInStateProvinces();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IStateProvinces) {
    return item.id;
  }

  registerChangeInStateProvinces() {
    this.eventSubscriber = this.eventManager.subscribe('stateProvincesListModification', response => this.loadAll());
  }
}
