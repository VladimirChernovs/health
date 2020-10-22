import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IDependent, Dependent } from 'app/shared/model/dependent.model';
import { DependentService } from './dependent.service';
import { IEnrollee } from 'app/shared/model/enrollee.model';
import { EnrolleeService } from 'app/entities/enrollee/enrollee.service';

@Component({
  selector: 'jhi-dependent-update',
  templateUrl: './dependent-update.component.html',
})
export class DependentUpdateComponent implements OnInit {
  isSaving = false;
  enrollees: IEnrollee[] = [];
  birthDateDp: any;

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required]],
    birthDate: [null, [Validators.required]],
    enrolleeId: [null, Validators.required],
  });

  constructor(
    protected dependentService: DependentService,
    protected enrolleeService: EnrolleeService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ dependent }) => {
      this.updateForm(dependent);

      this.enrolleeService.query().subscribe((res: HttpResponse<IEnrollee[]>) => (this.enrollees = res.body || []));
    });
  }

  updateForm(dependent: IDependent): void {
    this.editForm.patchValue({
      id: dependent.id,
      name: dependent.name,
      birthDate: dependent.birthDate,
      enrolleeId: dependent.enrolleeId,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const dependent = this.createFromForm();
    if (dependent.id !== undefined) {
      this.subscribeToSaveResponse(this.dependentService.update(dependent));
    } else {
      this.subscribeToSaveResponse(this.dependentService.create(dependent));
    }
  }

  private createFromForm(): IDependent {
    return {
      ...new Dependent(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      birthDate: this.editForm.get(['birthDate'])!.value,
      enrolleeId: this.editForm.get(['enrolleeId'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDependent>>): void {
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

  trackById(index: number, item: IEnrollee): any {
    return item.id;
  }
}
