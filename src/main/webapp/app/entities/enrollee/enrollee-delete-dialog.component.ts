import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IEnrollee } from 'app/shared/model/enrollee.model';
import { EnrolleeService } from './enrollee.service';

@Component({
  templateUrl: './enrollee-delete-dialog.component.html',
})
export class EnrolleeDeleteDialogComponent {
  enrollee?: IEnrollee;

  constructor(protected enrolleeService: EnrolleeService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.enrolleeService.delete(id).subscribe(() => {
      this.eventManager.broadcast('enrolleeListModification');
      this.activeModal.close();
    });
  }
}
