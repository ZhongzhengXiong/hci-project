import { NgModule } from '@angular/core';
import { IonicPageModule } from 'ionic-angular';
import { PicturePage } from './picture';
import {IonicImageLoader} from "ionic-image-loader";

@NgModule({
  declarations: [
    PicturePage,
  ],
  imports: [
    IonicImageLoader,
    IonicPageModule.forChild(PicturePage),
  ],
})
export class PicturePageModule {}
