import { Injectable } from '@angular/core';
import { HttpService } from "./HttpService";
import {Headers} from "@angular/http";

@Injectable()
export class NotifService {
  notifUrl = '/account/messages';

  constructor(private httpService: HttpService) {
    console.log('Hello NotifService Provider');
  }

  getNotif(token) {
    return this.httpService.get(this.notifUrl, null, new Headers({'Content-Type': 'application/json', 'Authorization': token}));
  }
}
