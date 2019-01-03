import { NgModule } from '@angular/core';
import { IonicPageModule } from 'ionic-angular';
import { HomePage } from './home';
import { IonicImageLoader } from "ionic-image-loader";
import {PipesModule} from "../../pipes/pipes.module";

@NgModule({
  declarations: [
    HomePage,
  ],
  imports: [
    IonicImageLoader,
    PipesModule,
    IonicPageModule.forChild(HomePage),
  ],
})
export class HomePageModule {}
