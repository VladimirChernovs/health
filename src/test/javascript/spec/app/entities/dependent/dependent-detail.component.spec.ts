import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { HealthTestModule } from '../../../test.module';
import { DependentDetailComponent } from 'app/entities/dependent/dependent-detail.component';
import { Dependent } from 'app/shared/model/dependent.model';

describe('Component Tests', () => {
  describe('Dependent Management Detail Component', () => {
    let comp: DependentDetailComponent;
    let fixture: ComponentFixture<DependentDetailComponent>;
    const route = ({ data: of({ dependent: new Dependent(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HealthTestModule],
        declarations: [DependentDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(DependentDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(DependentDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load dependent on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.dependent).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
