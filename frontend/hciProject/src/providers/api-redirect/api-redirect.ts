import { HttpClient, HttpHandler } from '@angular/common/http';
import { Injectable } from '@angular/core';

/*
  Generated class for the ApiRedirectProvider provider.

  See https://angular.io/guide/dependency-injection for more info on providers
  and Angular DI.
*/
@Injectable()
export class ApiRedirectProvider extends HttpClient {

  // public static host = 'http://101.132.75.236:8899';
  // public static host = 'http://localhost:8090';
  public static host = 'http://192.168.1.104:7477';

  constructor(private _handler: HttpHandler,) {
    super(_handler);
    console.log('Hello ApiRedirectProvider Provider');

    const methods = ['get', 'post', 'put'];
    methods.forEach(m => {
      this[m] = (url: string, ...params) => {
        if (url.startsWith('/')) {
          console.log(`api-redirect processing ${m} ${url}`);
          url = `${ApiRedirectProvider.host}${url}`
        }
        return super[m].apply(this, [url, ...params])
      }
    })
  }

}
