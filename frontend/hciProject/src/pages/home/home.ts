import { Component, ViewChild } from '@angular/core';
import {App, IonicPage, NavController, NavParams, Slides, ToastController} from 'ionic-angular';
import { EventDetailPage } from "../event-detail/event-detail";
import {CurrentUserProvider} from "../../providers/CurrentUser";
import {HomeService} from "../../providers/HomeService";

/**
 * Generated class for the HomePage page.
 *
 * See https://ionicframework.com/docs/components/#navigation for more info on
 * Ionic pages and navigation.
 */

@IonicPage()
@Component({
  selector: 'page-home',
  templateUrl: 'home.html',
})
export class HomePage {

  flowItems: any = null;
  text = '';

  @ViewChild(Slides) slides: Slides;

  constructor(public navCtrl: NavController, public navParams: NavParams,
              private appCtrl: App,
              public currentUser: CurrentUserProvider,
              public homeService: HomeService,
              public toast: ToastController
  ) {
    this.refresh();
  }

  ionViewDidLoad() {
    console.log('ionViewDidLoad HomePage');
  }

  input(ev: any) {
    this.text = ev.target.value;
  }

  clear(ev: any) {
    ev.target.value = '';
    this.text = '';
  }

  goToEventDetail(id) {
    this.appCtrl.getRootNav().push(EventDetailPage, { activityId: id })
  }


  refresh() {
    this.flowItems = null;
    this.text = '';
    this.homeService.getHome(this.currentUser.token).then(res => {
      console.log(res);
      if (res.success == false) {
        this.toast.create({ message: '请求错误', duration: 1500 }).present();
      }
      else {
        this.flowItems = res;
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
