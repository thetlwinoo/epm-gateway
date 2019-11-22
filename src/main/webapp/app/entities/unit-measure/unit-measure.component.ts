import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { filter, map } from 'rxjs/operators';
import { JhiEventManager } from 'ng-jhipster';

import { IUnitMeasure } from 'app/shared/model/unit-measure.model';
import { AccountService } from 'app/core/auth/account.service';
import { UnitMeasureService } from './unit-measure.service';

@Component({
  selector: 'jhi-unit-measure',
  templateUrl: './unit-measure.component.html'
})
export class UnitMeasureComponent implements OnInit, OnDestroy {
  unitMeasures: IUnitMeasure[];
  currentAccount: any;
  eventSubscriber: Subscription;
  currentSearch: string;

  constructor(
    protected unitMeasureService: UnitMeasureService,
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
      this.unitMeasureService
        .search({
          query: this.currentSearch
        })
        .pipe(
          filter((res: HttpResponse<IUnitMeasure[]>) => res.ok),
          map((res: HttpResponse<IUnitMeasure[]>) => res.body)
        )
        .subscribe((res: IUnitMeasure[]) => (this.unitMeasures = res));
      return;
    }
    this.unitMeasureService
      .query()
      .pipe(
        filter((res: HttpResponse<IUnitMeasure[]>) => res.ok),
        map((res: HttpResponse<IUnitMeasure[]>) => res.body)
      )
      .subscribe((res: IUnitMeasure[]) => {
        this.unitMeasures = res;
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
    this.registerChangeInUnitMeasures();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IUnitMeasure) {
    return item.id;
  }

  registerChangeInUnitMeasures() {
    this.eventSubscriber = this.eventManager.subscribe('unitMeasureListModification', response => this.loadAll());
  }
}
