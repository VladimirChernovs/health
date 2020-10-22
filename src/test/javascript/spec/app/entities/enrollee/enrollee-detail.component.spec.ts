import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { HealthTestModule } from '../../../test.module';
import { EnrolleeDetailComponent } from 'app/entities/enrollee/enrollee-detail.component';
import { Enrollee } from 'app/shared/model/enrollee.model';

describe('Component Tests', () => {
  describe('Enrollee Management Detail Component', () => {
    let comp: EnrolleeDetailComponent;
    let fixture: ComponentFixture<EnrolleeDetailComponent>;
    const route = ({ data: of({ enrollee: new Enrollee(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HealthTestModule],
        declarations: [EnrolleeDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(EnrolleeDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(EnrolleeDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load enrollee on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.enrollee).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
