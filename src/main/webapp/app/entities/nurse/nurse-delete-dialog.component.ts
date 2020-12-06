import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { INurse } from 'app/shared/model/nurse.model';
import { NurseService } from './nurse.service';

@Component({
  templateUrl: './nurse-delete-dialog.component.html',
})
export class NurseDeleteDialogComponent {
  nurse?: INurse;

  constructor(protected nurseService: NurseService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.nurseService.delete(id).subscribe(() => {
      this.eventManager.broadcast('nurseListModification');
      this.activeModal.close();
    });
  }
}
