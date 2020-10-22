import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { HealthTestModule } from '../../../test.module';
import { EnrolleeUpdateComponent } from 'app/entities/enrollee/enrollee-update.component';
import { EnrolleeService } from 'app/entities/enrollee/enrollee.service';
import { Enrollee } from 'app/shared/model/enrollee.model';

describe('Component Tests', () => {
  describe('Enrollee Management Update Component', () => {
    let comp: EnrolleeUpdateComponent;
    let fixture: ComponentFixture<EnrolleeUpdateComponent>;
    let service: EnrolleeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HealthTestModule],
        declarations: [EnrolleeUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(EnrolleeUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(EnrolleeUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(EnrolleeService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Enrollee(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new Enrollee();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
