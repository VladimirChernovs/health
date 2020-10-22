import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IEnrollee } from 'app/shared/model/enrollee.model';

@Component({
  selector: 'jhi-enrollee-detail',
  templateUrl: './enrollee-detail.component.html',
})
export class EnrolleeDetailComponent implements OnInit {
  enrollee: IEnrollee | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ enrollee }) => (this.enrollee = enrollee));
  }

  previousState(): void {
    window.history.back();
  }
}
