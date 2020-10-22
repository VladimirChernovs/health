import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IDependent } from 'app/shared/model/dependent.model';

type EntityResponseType = HttpResponse<IDependent>;
type EntityArrayResponseType = HttpResponse<IDependent[]>;

@Injectable({ providedIn: 'root' })
export class DependentService {
  public resourceUrl = SERVER_API_URL + 'api/dependents';

  constructor(protected http: HttpClient) {}

  create(dependent: IDependent): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(dependent);
    return this.http
      .post<IDependent>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(dependent: IDependent): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(dependent);
    return this.http
      .put<IDependent>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IDependent>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IDependent[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(dependent: IDependent): IDependent {
    const copy: IDependent = Object.assign({}, dependent, {
      birthDate: dependent.birthDate && dependent.birthDate.isValid() ? dependent.birthDate.format(DATE_FORMAT) : undefined,
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.birthDate = res.body.birthDate ? moment(res.body.birthDate) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((dependent: IDependent) => {
        dependent.birthDate = dependent.birthDate ? moment(dependent.birthDate) : undefined;
      });
    }
    return res;
  }
}
