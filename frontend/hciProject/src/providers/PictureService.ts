import { Injectable } from '@angular/core';
import { HttpService } from "./HttpService";
import {Headers} from "@angular/http";

@Injectable()
export class PictureService {
  pictureListUrl = '';
  pictureUrl = '';

  constructor(private httpService: HttpService) {
    console.log('Hello PictureService Provider');
  }

  getNotif(token) {
    return this.httpService.get(this.pictureUrl, null, new Headers({'Content-Type': 'application/json', 'Authorization': token}));
  }
}
