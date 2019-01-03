import { BrowserModule } from '@angular/platform-browser';
import { NgModule, ErrorHandler } from '@angular/core';
import { IonicApp, IonicModule, IonicErrorHandler } from 'ionic-angular';
import { MyApp } from './app.component';

import { StatusBar } from '@ionic-native/status-bar';
import { SplashScreen } from '@ionic-native/splash-screen';
// import { HttpModule } from "@angular/http";
import { HTTP_INTERCEPTORS, HttpClientModule } from "@angular/common/http";
import { StartupPageModule } from "../pages/startup/startup.module";
import { MockProvider } from '../providers/mock/mock';
import { DataProvider } from "../providers/data/data";
import { IonicImageLoader } from "ionic-image-loader";
import { LoadingCoverProvider } from '../providers/loading-cover/loading-cover';
import { ImagePicker } from "@ionic-native/image-picker";
import {LoginPageModule} from "../pages/login/login.module";
import {HttpModule} from "@angular/http";
import {HttpService} from "../providers/HttpService";
import {CurrentUserProvider} from "../providers/CurrentUser";
import {LoginService} from "../providers/LoginService";
import {HomeService} from "../providers/HomeService";
import {NotifService} from "../providers/NotifService";
import {EventService} from "../providers/EventService";
import { FileTransfer, FileUploadOptions, FileTransferObject }from'@ionic-native/file-transfer';
import { File } from '@ionic-native/file';



@NgModule({
  declarations: [
    MyApp
  ],
  imports: [
    IonicModule.forRoot(MyApp),
    StartupPageModule,
    LoginPageModule,
    BrowserModule,
    IonicImageLoader.forRoot(),
    HttpClientModule,
    HttpModule
  ],
  bootstrap: [IonicApp],
  entryComponents: [
    MyApp
  ],
  providers: [
    {
      provide: HTTP_INTERCEPTORS,
      useClass: MockProvider,
      multi: true,
    },
    DataProvider,
    ImagePicker,
    File,
    FileTransferObject,
    FileTransfer,
    StatusBar,
    SplashScreen,
    { provide: ErrorHandler, useClass: IonicErrorHandler },
    LoadingCoverProvider,
    HttpService,
    EventService,
    CurrentUserProvider,
    LoginService,
    HomeService,
    NotifService
  ]
})
export class AppModule {
}
