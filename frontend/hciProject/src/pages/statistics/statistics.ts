import { Component } from '@angular/core';
import {IonicPage, NavController, NavParams, ToastController} from 'ionic-angular';
import {CurrentUserProvider} from "../../providers/CurrentUser";
import {EventService} from "../../providers/EventService";

/**
 * Generated class for the StatisticsPage page.
 *
 * See https://ionicframework.com/docs/components/#navigation for more info on
 * Ionic pages and navigation.
 */

@IonicPage()
@Component({
  selector: 'page-statistics',
  templateUrl: 'statistics.html',
})
export class StatisticsPage {
  participateNum: number;
  createNum: number;
  mostFrequentType: any;
  mostFrequentPlace: any;
  reviewNum: number;
  uploadPhotoNum: number;

  constructor(public navCtrl: NavController, public navParams: NavParams,
              public currentUser: CurrentUserProvider,
              public eventService: EventService,
              public toast: ToastController
  ) {
    this.eventService.getStatistics(this.currentUser.token).then(res => {
      console.log(res);
      if (res.success == false) {
        this.toast.create({ message: '请求错误', duration: 1500 }).present();
      }
      else {
        this.participateNum = res.participateNum;
        this.createNum = res.createNum;
        this.mostFrequentType = res.mostFrequentType;
        this.mostFrequentPlace= res.mostFrequentPlace;
        this.reviewNum = res.reviewNum;
        this.uploadPhotoNum = res.uploadPhotoNum;
      }
    });
  }

  ionViewDidLoad() {
    console.log('ionViewDidLoad StatisticsPage');
  }

}
