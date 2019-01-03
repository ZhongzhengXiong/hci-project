import { Injectable } from '@angular/core';
import { HttpService } from "./HttpService";
import {Headers} from "@angular/http";

@Injectable()
export class EventService {
  eventByIdUrl = '/activities';
  eventByInviteUrl = '/activities';

  newEventUrl = '';
  addEventUrl = '';
  statisticsUrl = '/account/stat-info';

  addEventPictureUrl = '';
  addEventCommentUrl = '';

  constructor(private httpService: HttpService) {
    console.log('Hello EventService Provider');
  }

  getEventById(id, token) {
    return this.httpService.get(this.eventByIdUrl + '/' + id, null, new Headers({'Content-Type': 'application/json', 'Authorization': token}));
  }

  getEventByInvite(code, token) {
    return this.httpService.get(this.eventByInviteUrl + '?invitingcode=' + code, null, new Headers({'Content-Type': 'application/json', 'Authorization': token}));
  }

  joinEvent(id, token) {
    return this.httpService.post('/activities/' + id + '/users', null, new Headers({'Content-Type': 'application/json', 'Authorization': token}));
  }

  getStatistics(token) {
    return this.httpService.get(this.statisticsUrl, null, new Headers({'Content-Type': 'application/json', 'Authorization': token}));
  }

  getPictureById(id, token) {
    return this.httpService.get('/activities/' + id + '/photos', null, new Headers({'Content-Type': 'application/json', 'Authorization': token}));
  }

  getCommentById(id, token) {
    return this.httpService.get('/activities/' + id + '/reviews', null, new Headers({'Content-Type': 'application/json', 'Authorization': token}));
  }

  getNotiById(id, token) {
    return this.httpService.get('/activities/' + id + '/notices', null, new Headers({'Content-Type': 'application/json', 'Authorization': token}));
  }

  addComment(id, content, token) {
    return this.httpService.post('/activities/' + id + '/reviews', {content: content}, new Headers({'Content-Type': 'application/json', 'Authorization': token}));
  }

  addNoti(id, title, content, token) {
    return this.httpService.post('/activities/' + id + '/notices', {title: title, content: content}, new Headers({'Content-Type': 'application/json', 'Authorization': token}));
  }

  uploadPicture(id, imageUri: string, token) {
    return this.getBlobFromUri(imageUri)
      .then(blob => {
        const formData = new FormData();
        formData.append('file', blob);
        return this.httpService.post('/activities/' + id + '/photos', formData, new Headers({'Content-Type': 'application/json', 'Authorization': token}))
      })
  }

  getBlobFromUri(uri): Promise<Blob> {
    return new Promise<Blob>(resolve => {
      const xhr = new XMLHttpRequest();
      xhr.open('GET', uri, true);
      xhr.responseType = 'blob';
      xhr.onload = function (e) {
        resolve(this.response)
      };
      xhr.send()
    })
  }
}
