import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IEnrollee, Enrollee } from 'app/shared/model/enrollee.model';
import { EnrolleeService } from './enrollee.service';

@Component({
  selector: 'jhi-enrollee-update',
  templateUrl: './enrollee-update.component.html',
})
export class EnrolleeUpdateComponent implements OnInit {
  isSaving = false;
  birthDateDp: any;

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required]],
    activationStatus: [null, [Validators.required]],
    birthDate: [null, [Validators.required]],
    phoneNumber: [null, [Validators.pattern('^[+]*[(]{0,1}[0-9]{1,4}[)]{0,1}[-\\s\\./0-9]*$')]],
  });

  constructor(protected enrolleeService: EnrolleeService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ enrollee }) => {
      this.updateForm(enrollee);
    });
  }

  updateForm(enrollee: IEnrollee): void {
    this.editForm.patchValue({
      id: enrollee.id,
      name: enrollee.name,
      activationStatus: enrollee.activationStatus,
      birthDate: enrollee.birthDate,
      phoneNumber: enrollee.phoneNumber,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const enrollee = this.createFromForm();
    if (enrollee.id !== undefined) {
      this.subscribeToSaveResponse(this.enrolleeService.update(enrollee));
    } else {
      this.subscribeToSaveResponse(this.enrolleeService.create(enrollee));
    }
  }

  private createFromForm(): IEnrollee {
    return {
      ...new Enrollee(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      activationStatus: this.editForm.get(['activationStatus'])!.value,
      birthDate: this.editForm.get(['birthDate'])!.value,
      phoneNumber: this.editForm.get(['phoneNumber'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEnrollee>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }
}
