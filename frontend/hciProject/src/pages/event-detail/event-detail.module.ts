import { NgModule } from '@angular/core';
import { IonicPageModule } from 'ionic-angular';
import { EventDetailPage } from './event-detail';
import { IonicImageLoader } from "ionic-image-loader";

@NgModule({
  declarations: [
    EventDetailPage,
  ],
  imports: [
    IonicImageLoader,
    IonicPageModule.forChild(EventDetailPage),
  ],
})
export class EventDetailPageModule {}
