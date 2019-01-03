import {
  HTTP_INTERCEPTORS,
  HttpEvent,
  HttpHandler,
  HttpInterceptor,
  HttpRequest,
  HttpResponse
} from '@angular/common/http';
import { Injectable, NgModule } from '@angular/core';
import { Observable } from "rxjs";
import { delay, mapTo } from "rxjs/operators";
import { of } from "rxjs/observable/of";
import { mockData } from "./mock-data";
import escapeRegExp from 'lodash/escapeRegExp'

/*
  Generated class for the MockProvider provider.

  See https://angular.io/guide/dependency-injection for more info on providers
  and Angular DI.
*/
@Injectable()
export class MockProvider implements HttpInterceptor {
  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    // if (req.url.startsWith("http"))
    //   return next.handle(req);
    // console.log(req);
    const key = `${req.method} ${req.url}`;
    const mockDatum = mockData[
      Object.keys(mockData).find(k => {
        k = escapeRegExp(k);
        k = k.replace(/:[A-Za-z]*(\\\/|$)/, "\\d+$1");
        // console.log(k, new RegExp(k), key);
        return new RegExp(k).test(key);
      })];
    const response = {
      status: 200,
    };
    if (mockDatum && mockDatum !== true)
      response['body'] = mockDatum;
    console.log(mockDatum);
    return mockDatum ?
      of(null).pipe(mapTo(new HttpResponse(response)), delay(500))
      : next.handle(req);
  }

}
