import { Injectable } from '@angular/core';
import { HttpService } from "./HttpService";
import {Headers} from "@angular/http";

@Injectable()
export class HomeService {
  homeUrl = "/account/activities";
  pictureUrl = "/account/photos";

  constructor(private httpService: HttpService) {
    console.log('Hello HomeService Provider');
  }

  getHome(token) {
    return this.httpService.get(this.homeUrl, null, new Headers({'Content-Type': 'application/json', 'Authorization': token}));
  }

  getPicture(token) {
    return this.httpService.get(this.pictureUrl, null, new Headers({'Content-Type': 'application/json', 'Authorization': token}));
  }

}
