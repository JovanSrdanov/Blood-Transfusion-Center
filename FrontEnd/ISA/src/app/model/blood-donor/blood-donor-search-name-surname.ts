import {SortType} from '../sort-type'
export interface BloodDonorSearchNameSurname{
    //Search
    name : string;
    surname : string;
    //Paging and sorting
    page : number;
    pageSize : number;
    sortByField : string;
    sortType : SortType;
}
