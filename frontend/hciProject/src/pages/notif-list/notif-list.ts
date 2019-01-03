import { Component } from '@angular/core';
import {IonicPage, NavController, NavParams, App, AlertController, ToastController} from 'ionic-angular';
import {LoadingCoverProvider} from "../../providers/loading-cover/loading-cover";
import {DataProvider} from "../../providers/data/data";
import {NotifPreview} from "./notif-preview";
import {Observable} from "rxjs";
import {NotifService} from "../../providers/NotifService";
import {CurrentUserProvider} from "../../providers/CurrentUser";

/**
 * Generated class for the NotifListPage page.
 *
 * See https://ionicframework.com/docs/components/#navigation for more info on
 * Ionic pages and navigation.
 */

@IonicPage()
@Component({
  selector: 'page-notif-list',
  templateUrl: 'notif-list.html',
})
export class NotifListPage {

  notifItems: any = null;

  constructor(public navCtrl: NavController, public navParams: NavParams,
              public alertCtrl: AlertController,
              public currentUser: CurrentUserProvider,
              public notifService: NotifService,
              public toast: ToastController
  ) {
    this.refresh();
  }


  ionViewDidLoad() {
    console.log('ionViewDidLoad NotifListPage');
  }

  doAlert(notifItem) {
    let alert = this.alertCtrl.create({
      title: notifItem.title,
      subTitle: notifItem.content,
      buttons: ['Ok']
    });

    alert.present();
  }

  refresh() {
    this.notifItems = null;

    this.notifService.getNotif(this.currentUser.token).then(res => {
      console.log(res);
      if (res.success == false) {
        this.toast.create({ message: '请求错误', duration: 1500 }).present();
      }
      else {
        this.notifItems = res;
      }
    });
  }

  doRefresh(refresher){
    setTimeout(()=>{
      refresher.complete();
    },2000)
    this.refresh();
  }
}
