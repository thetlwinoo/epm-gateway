import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { filter, map } from 'rxjs/operators';
import { JhiEventManager } from 'ng-jhipster';

import { ISupplierImportedDocument } from 'app/shared/model/supplier-imported-document.model';
import { AccountService } from 'app/core/auth/account.service';
import { SupplierImportedDocumentService } from './supplier-imported-document.service';

@Component({
  selector: 'jhi-supplier-imported-document',
  templateUrl: './supplier-imported-document.component.html'
})
export class SupplierImportedDocumentComponent implements OnInit, OnDestroy {
  supplierImportedDocuments: ISupplierImportedDocument[];
  currentAccount: any;
  eventSubscriber: Subscription;
  currentSearch: string;

  constructor(
    protected supplierImportedDocumentService: SupplierImportedDocumentService,
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
      this.supplierImportedDocumentService
        .search({
          query: this.currentSearch
        })
        .pipe(
          filter((res: HttpResponse<ISupplierImportedDocument[]>) => res.ok),
          map((res: HttpResponse<ISupplierImportedDocument[]>) => res.body)
        )
        .subscribe((res: ISupplierImportedDocument[]) => (this.supplierImportedDocuments = res));
      return;
    }
    this.supplierImportedDocumentService
      .query()
      .pipe(
        filter((res: HttpResponse<ISupplierImportedDocument[]>) => res.ok),
        map((res: HttpResponse<ISupplierImportedDocument[]>) => res.body)
      )
      .subscribe((res: ISupplierImportedDocument[]) => {
        this.supplierImportedDocuments = res;
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
    this.registerChangeInSupplierImportedDocuments();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: ISupplierImportedDocument) {
    return item.id;
  }

  registerChangeInSupplierImportedDocuments() {
    this.eventSubscriber = this.eventManager.subscribe('supplierImportedDocumentListModification', response => this.loadAll());
  }
}
