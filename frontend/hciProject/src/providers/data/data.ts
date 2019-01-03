import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from "rxjs";
import { EventPreview } from "../../pages/home/event-preview";
import { NotifPreview } from "../../pages/notif-list/notif-preview"
import {PicPreview} from "../../pages/picture/pic-preview";

/*
  Generated class for the DataProvider provider.

  See https://angular.io/guide/dependency-injection for more info on providers
  and Angular DI.
*/
@Injectable()
export class DataProvider {

  private detailUrl = '/api/event/detail';
  private slidesUrl = '/api/event/home-slides';
  private eventTypeListUrl = '/api/event-type/list';
  private eventsJoinedUrl = '/api/personal/events-joined';
  private eventsReleasedUrl = '/api/personal/events-released';
  private checkinUrl = '/api/event/checkin';

  private flowUrl = '/api/event/home-flow';
  private myPictureUrl = '/api/picture/my-picture';
  private notifListUrl = '/api/notif/notif-list';
  private eventPictureUrl = '/api/picture/event-picture';
  private eventCommentUrl = '/api/event/event-comment';

  constructor(public http: HttpClient) {
    console.log('Hello DataProvider Provider');
  }

  getHomeSlides(): Observable<EventPreview[]> {
    return this.http.get<EventPreview[]>(this.slidesUrl)
  }

  getEventTypeList(): Observable<any> {
    return this.http.get(this.eventTypeListUrl)
  }

  getEventsJoined(): Observable<EventPreview[]> {
    return this.http.get<EventPreview[]>(this.eventsJoinedUrl)
  }

  getEventsReleased(): Observable<EventPreview[]> {
    return this.http.get<EventPreview[]>(this.eventsReleasedUrl)
  }

  getEventCheckin(eventId): Observable<any> {
    return this.http.get(`${this.checkinUrl}/${eventId}`)

  }



  getDetail(eventId): Observable<any> {
    return this.http.get(`${this.detailUrl}/${eventId}`)
  }

  getHomeFlow(): Observable<EventPreview[]> {
    return this.http.get<EventPreview[]>(this.flowUrl)
  }

  getNotifList(): Observable<NotifPreview[]> {
    return this.http.get<NotifPreview[]>(this.notifListUrl)
  }

  getMyPicture(): Observable<PicPreview[]> {
    return this.http.get<PicPreview[]>(this.myPictureUrl)
  }

  getEventPicture(eventId): Observable<any> {
    return this.http.get(`${this.eventPictureUrl}/${eventId}`)
  }

  getEventComment(eventId): Observable<any> {
    return this.http.get(`${this.eventCommentUrl}/${eventId}`)
  }

}
