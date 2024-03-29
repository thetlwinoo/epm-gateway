import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICountries } from 'app/shared/model/countries.model';
import { CountriesService } from './countries.service';

@Component({
  selector: 'jhi-countries-delete-dialog',
  templateUrl: './countries-delete-dialog.component.html'
})
export class CountriesDeleteDialogComponent {
  countries: ICountries;

  constructor(protected countriesService: CountriesService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.countriesService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'countriesListModification',
        content: 'Deleted an countries'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-countries-delete-popup',
  template: ''
})
export class CountriesDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ countries }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(CountriesDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.countries = countries;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/countries', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/countries', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          }
        );
      }, 0);
    });
  }

  ngOnDestroy() {
    this.ngbModalRef = null;
  }
}
