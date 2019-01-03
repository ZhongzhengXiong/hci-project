import { Component } from '@angular/core';
import {IonicPage, NavController, NavParams, App} from 'ionic-angular';

import {LoadingCoverProvider} from "../../providers/loading-cover/loading-cover";
import {DataProvider} from "../../providers/data/data";
import {StatisticsPage} from "../statistics/statistics";
import {AboutPage} from "../about/about";
import {ChangePasswordPage} from "../change-password/change-password";
import {LoginPage} from "../login/login";
import {CurrentUserProvider} from "../../providers/CurrentUser";


/**
 * Generated class for the PersonalPage page.
 *
 * See https://ionicframework.com/docs/components/#navigation for more info on
 * Ionic pages and navigation.
 */

@IonicPage()
@Component({
  selector: 'page-personal',
  templateUrl: 'personal.html',
})
export class PersonalPage {

  constructor(public navCtrl: NavController, public navParams: NavParams,
              private appCtrl: App,
              public currentUser: CurrentUserProvider) {
  }

  ionViewDidLoad() {
    console.log('ionViewDidLoad PersonalPage');
  }

  goToEventsStatistics() {
    this.appCtrl.getRootNav().push(StatisticsPage, {});
  }

  goToPersonalInformation() {
    this.appCtrl.getRootNav().push(ChangePasswordPage, {});
  }

  goToOurs() {
    this.appCtrl.getRootNav().push(AboutPage, {});
  }

  logOut() {
    this.currentUser.clear();
    this.appCtrl.getRootNavs()[0].setRoot(LoginPage);
  }

}
