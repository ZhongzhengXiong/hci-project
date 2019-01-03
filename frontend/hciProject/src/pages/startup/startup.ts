import { Component } from '@angular/core';
import { IonicPage, NavController, NavParams } from 'ionic-angular';
import { HomePage } from "../home/home";
import { NotifListPage } from "../notif-list/notif-list";
import { PersonalPage } from "../personal/personal";
import { NewEventPage } from "../new-event/new-event";
import {PicturePage} from "../picture/picture";

/**
 * Generated class for the StartupPage page.
 *
 * See https://ionicframework.com/docs/components/#navigation for more info on
 * Ionic pages and navigation.
 */

@IonicPage()
@Component({
  selector: 'page-startup',
  templateUrl: 'startup.html',
})
export class StartupPage {
  pages: Array<{ title: string, component: any, tabIcon: string }>;

  constructor(public navCtrl: NavController, public navParams: NavParams) {
    // set our app's pages
    this.pages = [
      { title: '活动', component: HomePage, tabIcon: 'home' },
      { title: '相册', component: PicturePage, tabIcon: 'images' },
      { title: '新建活动', component: NewEventPage, tabIcon: 'add' },
      { title: '消息', component: NotifListPage, tabIcon: 'notifications' },
      { title: '个人', component: PersonalPage, tabIcon: 'person' },
    ];
  }

  ionViewDidLoad() {
    console.log('ionViewDidLoad StartupPage');
  }

}
