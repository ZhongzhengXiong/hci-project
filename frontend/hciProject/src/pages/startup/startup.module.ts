import { NgModule } from '@angular/core';
import { IonicPageModule } from 'ionic-angular';
import { StartupPage } from './startup';
import { HomePageModule } from "../home/home.module";
import { EventDetailPageModule } from "../event-detail/event-detail.module";
import { NotifListPageModule } from "../notif-list/notif-list.module";
import { PersonalPageModule } from "../personal/personal.module";
import { SuperTabsModule } from "ionic2-super-tabs";
import { NewEventPageModule } from "../new-event/new-event.module";
import {PicturePageModule} from "../picture/picture.module";

@NgModule({
  declarations: [
    StartupPage,
  ],
  imports: [
    HomePageModule,
    EventDetailPageModule,
    PicturePageModule,
    NewEventPageModule,
    NotifListPageModule,
    PersonalPageModule,
    IonicPageModule.forChild(StartupPage),
    SuperTabsModule.forRoot(),
  ],
})
export class StartupPageModule {}
