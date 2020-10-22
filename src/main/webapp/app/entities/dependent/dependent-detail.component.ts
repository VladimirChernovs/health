import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IDependent } from 'app/shared/model/dependent.model';

@Component({
  selector: 'jhi-dependent-detail',
  templateUrl: './dependent-detail.component.html',
})
export class DependentDetailComponent implements OnInit {
  dependent: IDependent | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ dependent }) => (this.dependent = dependent));
  }

  previousState(): void {
    window.history.back();
  }
}
