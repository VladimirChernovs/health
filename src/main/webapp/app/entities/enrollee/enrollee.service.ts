import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IEnrollee } from 'app/shared/model/enrollee.model';

type EntityResponseType = HttpResponse<IEnrollee>;
type EntityArrayResponseType = HttpResponse<IEnrollee[]>;

@Injectable({ providedIn: 'root' })
export class EnrolleeService {
  public resourceUrl = SERVER_API_URL + 'api/enrollees';

  constructor(protected http: HttpClient) {}

  create(enrollee: IEnrollee): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(enrollee);
    return this.http
      .post<IEnrollee>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(enrollee: IEnrollee): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(enrollee);
    return this.http
      .put<IEnrollee>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IEnrollee>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IEnrollee[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(enrollee: IEnrollee): IEnrollee {
    const copy: IEnrollee = Object.assign({}, enrollee, {
      birthDate: enrollee.birthDate && enrollee.birthDate.isValid() ? enrollee.birthDate.format(DATE_FORMAT) : undefined,
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
      res.body.forEach((enrollee: IEnrollee) => {
        enrollee.birthDate = enrollee.birthDate ? moment(enrollee.birthDate) : undefined;
      });
    }
    return res;
  }
}
