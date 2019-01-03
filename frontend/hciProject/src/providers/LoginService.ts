import { Injectable } from '@angular/core';
import { HttpService } from "./HttpService";
import {Headers} from "@angular/http";

@Injectable()
export class LoginService {
  loginUrl = '/token';
  registerUrl = '/users';
  changePasswordUrl = '/users/reset_password';

  constructor(private httpService: HttpService) {
    console.log('Hello LoginService Provider');
  }

  login(tel, password) {
    return this.httpService.post(this.loginUrl, {phone: tel, password: password}, new Headers({'Content-Type': 'application/json'}));
  }

  register() {
    //return this.httpService.post(this.registerUrl, null);
  }

  changePassword(oldPassword, newPassword, token) {
    return this.httpService.post(this.changePasswordUrl, {oldPassword: oldPassword, newPassword: newPassword}, new Headers({'Content-Type': 'application/json', 'Authorization': token}));
  }

}
