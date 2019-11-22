import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { filter, map } from 'rxjs/operators';
import { JhiEventManager } from 'ng-jhipster';

import { IVehicleTemperatures } from 'app/shared/model/vehicle-temperatures.model';
import { AccountService } from 'app/core/auth/account.service';
import { VehicleTemperaturesService } from './vehicle-temperatures.service';

@Component({
  selector: 'jhi-vehicle-temperatures',
  templateUrl: './vehicle-temperatures.component.html'
})
export class VehicleTemperaturesComponent implements OnInit, OnDestroy {
  vehicleTemperatures: IVehicleTemperatures[];
  currentAccount: any;
  eventSubscriber: Subscription;
  currentSearch: string;

  constructor(
    protected vehicleTemperaturesService: VehicleTemperaturesService,
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
      this.vehicleTemperaturesService
        .search({
          query: this.currentSearch
        })
        .pipe(
          filter((res: HttpResponse<IVehicleTemperatures[]>) => res.ok),
          map((res: HttpResponse<IVehicleTemperatures[]>) => res.body)
        )
        .subscribe((res: IVehicleTemperatures[]) => (this.vehicleTemperatures = res));
      return;
    }
    this.vehicleTemperaturesService
      .query()
      .pipe(
        filter((res: HttpResponse<IVehicleTemperatures[]>) => res.ok),
        map((res: HttpResponse<IVehicleTemperatures[]>) => res.body)
      )
      .subscribe((res: IVehicleTemperatures[]) => {
        this.vehicleTemperatures = res;
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
    this.registerChangeInVehicleTemperatures();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IVehicleTemperatures) {
    return item.id;
  }

  registerChangeInVehicleTemperatures() {
    this.eventSubscriber = this.eventManager.subscribe('vehicleTemperaturesListModification', response => this.loadAll());
  }
}
