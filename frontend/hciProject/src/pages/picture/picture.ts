import { Component } from '@angular/core';
import {IonicPage, NavController, NavParams, App, ToastController} from 'ionic-angular';
import {DataProvider} from "../../providers/data/data";
import {LoadingCoverProvider} from "../../providers/loading-cover/loading-cover";
import {PicPreview} from "./pic-preview";
import {Observable} from "rxjs";
import {EventDetailPage} from "../event-detail/event-detail";
import {HomeService} from "../../providers/HomeService";
import {CurrentUserProvider} from "../../providers/CurrentUser";

/**
 * Generated class for the PicturePage page.
 *
 * See https://ionicframework.com/docs/components/#navigation for more info on
 * Ionic pages and navigation.
 */

@IonicPage()
@Component({
  selector: 'page-picture',
  templateUrl: 'picture.html',
})
export class PicturePage {

  picItems: any = null;

  constructor(public navCtrl: NavController, public navParams: NavParams,
              private appCtrl: App,
              public currentUser: CurrentUserProvider,
              public homeService: HomeService,
              public toast: ToastController) {
    this.refresh();
  }


  ionViewDidLoad() {
    console.log('ionViewDidLoad PicturePage');
  }

  goToEventDetail(id) {
    this.appCtrl.getRootNav().push(EventDetailPage, { activityId: id })
  }

  refresh() {
    this.picItems = null;
    this.homeService.getPicture(this.currentUser.token).then(res => {
      console.log(res);
      if (res.success == false) {
        this.toast.create({ message: '请求错误', duration: 1500 }).present();
      }
      else {
        this.picItems = res;
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
