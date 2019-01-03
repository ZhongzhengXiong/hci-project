import { NgModule } from '@angular/core';
import { IonicPageModule } from 'ionic-angular';
import { LoginPage } from './login';
import {StartupPageModule} from "../startup/startup.module";
import {IonicImageLoader} from "ionic-image-loader";

@NgModule({
  declarations: [
    LoginPage,
  ],
  imports: [
    IonicImageLoader,
    StartupPageModule,
    IonicPageModule.forChild(LoginPage),
  ],
})
export class LoginPageModule {}
