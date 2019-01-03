import { NgModule } from '@angular/core';
import { IonicPageModule } from 'ionic-angular';
import { PersonalPage } from './personal';
import { IonicImageLoader} from "ionic-image-loader";
import {StatisticsPageModule} from "../statistics/statistics.module";
import {ChangePasswordPageModule} from "../change-password/change-password.module";
import {AboutPageModule} from "../about/about.module";


@NgModule({
  declarations: [
    PersonalPage,
  ],
  imports: [
    StatisticsPageModule,
    ChangePasswordPageModule,
    AboutPageModule,
    IonicImageLoader,
    IonicPageModule.forChild(PersonalPage)
  ],
})
export class PersonalPageModule {}
