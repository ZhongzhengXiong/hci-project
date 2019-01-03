import { NgModule } from '@angular/core';
import { IonicPageModule } from 'ionic-angular';
import { NewEventPage } from './new-event';
import { ComponentsModule } from "../../components/components.module";
import {IonicImageLoader} from "ionic-image-loader";

@NgModule({
  declarations: [
    NewEventPage,
  ],
  imports: [
    IonicImageLoader,
    IonicPageModule.forChild(NewEventPage),
    ComponentsModule
  ],
})
export class NewEventPageModule {}
