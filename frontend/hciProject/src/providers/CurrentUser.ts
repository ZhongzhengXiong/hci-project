import { Injectable } from '@angular/core';

@Injectable()
export class CurrentUserProvider {
  name = null;
  id = null;
  token = null;
  avatar = null;

  url = "http://54.245.150.228:7477";
  homeImageUrl = this.url + "/introphoto/";
  pictureImageUrl = this.url + "/activityphoto/";
  avatarImageUrl = this.url + "/avatar/";

  constructor() {
    console.log('Hello CurrentUserProvider Provider');
  }

  set(name: any, id: any, token: any, avatar: any) {
    this.name = name;
    this.id = id;
    this.token = token;
    this.avatar = avatar;
  }

  clear() {
    this.name = null;
    this.id = null;
    this.token = null;
    this.avatar = null;
  }
}
