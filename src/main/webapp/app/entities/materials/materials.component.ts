import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { filter, map } from 'rxjs/operators';
import { JhiEventManager } from 'ng-jhipster';

import { IMaterials } from 'app/shared/model/materials.model';
import { AccountService } from 'app/core/auth/account.service';
import { MaterialsService } from './materials.service';

@Component({
  selector: 'jhi-materials',
  templateUrl: './materials.component.html'
})
export class MaterialsComponent implements OnInit, OnDestroy {
  materials: IMaterials[];
  currentAccount: any;
  eventSubscriber: Subscription;
  currentSearch: string;

  constructor(
    protected materialsService: MaterialsService,
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
      this.materialsService
        .search({
          query: this.currentSearch
        })
        .pipe(
          filter((res: HttpResponse<IMaterials[]>) => res.ok),
          map((res: HttpResponse<IMaterials[]>) => res.body)
        )
        .subscribe((res: IMaterials[]) => (this.materials = res));
      return;
    }
    this.materialsService
      .query()
      .pipe(
        filter((res: HttpResponse<IMaterials[]>) => res.ok),
        map((res: HttpResponse<IMaterials[]>) => res.body)
      )
      .subscribe((res: IMaterials[]) => {
        this.materials = res;
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
    this.registerChangeInMaterials();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IMaterials) {
    return item.id;
  }

  registerChangeInMaterials() {
    this.eventSubscriber = this.eventManager.subscribe('materialsListModification', response => this.loadAll());
  }
}
