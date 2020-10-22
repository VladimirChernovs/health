import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { HealthTestModule } from '../../../test.module';
import { DependentUpdateComponent } from 'app/entities/dependent/dependent-update.component';
import { DependentService } from 'app/entities/dependent/dependent.service';
import { Dependent } from 'app/shared/model/dependent.model';

describe('Component Tests', () => {
  describe('Dependent Management Update Component', () => {
    let comp: DependentUpdateComponent;
    let fixture: ComponentFixture<DependentUpdateComponent>;
    let service: DependentService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HealthTestModule],
        declarations: [DependentUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(DependentUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(DependentUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(DependentService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Dependent(123);
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
        const entity = new Dependent();
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
