import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IDependent } from 'app/shared/model/dependent.model';
import { DependentService } from './dependent.service';

@Component({
  templateUrl: './dependent-delete-dialog.component.html',
})
export class DependentDeleteDialogComponent {
  dependent?: IDependent;

  constructor(protected dependentService: DependentService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.dependentService.delete(id).subscribe(() => {
      this.eventManager.broadcast('dependentListModification');
      this.activeModal.close();
    });
  }
}
