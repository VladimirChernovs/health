import { Moment } from 'moment';

export interface IDependent {
  id?: number;
  name?: string;
  birthDate?: Moment;
  enrolleeName?: string;
  enrolleeId?: number;
}

export class Dependent implements IDependent {
  constructor(
    public id?: number,
    public name?: string,
    public birthDate?: Moment,
    public enrolleeName?: string,
    public enrolleeId?: number
  ) {}
}
