import { Moment } from 'moment';
import { IDependent } from 'app/shared/model/dependent.model';

export interface IEnrollee {
  id?: number;
  name?: string;
  activationStatus?: boolean;
  birthDate?: Moment;
  phoneNumber?: string;
  dependents?: IDependent[];
}

export class Enrollee implements IEnrollee {
  constructor(
    public id?: number,
    public name?: string,
    public activationStatus?: boolean,
    public birthDate?: Moment,
    public phoneNumber?: string,
    public dependents?: IDependent[]
  ) {
    this.activationStatus = this.activationStatus || false;
  }
}
