import { Component } from '@angular/core';
import {IonicPage, NavController, NavParams, ToastController, App} from 'ionic-angular';
import {ImagePicker, ImagePickerOptions} from "@ionic-native/image-picker";
import * as moment from 'moment';
import { Moment } from "moment";
import { LoadingCoverProvider } from "../../providers/loading-cover/loading-cover";
import { DataProvider } from "../../providers/data/data";
import { Observable } from "rxjs";
import {CurrentUserProvider} from "../../providers/CurrentUser";
import {EventService} from "../../providers/EventService";
import { FileTransfer, FileUploadOptions, FileTransferObject }from'@ionic-native/file-transfer';
import { File } from '@ionic-native/file';
import {HomePage} from "../home/home";
import {SuperTabsController} from "ionic2-super-tabs";

/**
 * Generated class for the NewEventPage page.
 *
 * See https://ionicframework.com/docs/components/#navigation for more info on
 * Ionic pages and navigation.
 */

@IonicPage()
@Component({
  selector: 'page-new-event',
  templateUrl: 'new-event.html',
})
export class NewEventPage {

  button = 1;

  name: string;
  place: string;
  type: string;
  detail: string;
  userLimit: number;
  startTimeMinLimit: string;
  endTimeMinLimit: string;
  timeMaxLimit: string;

  constructor(public navCtrl: NavController, public navParams: NavParams,
              private imagePicker: ImagePicker,
              public currentUser: CurrentUserProvider,
              public eventService: EventService,
              public toast: ToastController,
              private file:File,
              private fileTransfer:FileTransferObject,
              public superTagController: SuperTabsController
  ) {
    const now = moment();
    this.startTimeMinLimit = now.format();
    this.endTimeMinLimit = now.add(1, "minute").format();
    this.timeMaxLimit = now.add(5, "y").format("YYYY");

  }



  _startTime: Moment = moment().add(1, "d").hour(18).minute(0);
  _endTime: Moment = this._startTime.clone().add(2, "h");


  get startTime() {
    return this._startTime.format()
  }

  set startTime(time) {
    this._startTime = moment.max(moment(time), moment());
    if (!this._startTime.isBefore(this._endTime))
      this._endTime = this._startTime.clone().add(2, "h")
  }

  get endTime() {
    return this._endTime.format()
  }

  set endTime(time) {
    const now = moment();
    this._endTime = moment.max(moment(time), now.clone().add(1, "minute"));
    if (!this._endTime.isAfter(this._startTime))
      this._startTime = moment.max(this._endTime.clone().subtract(2, "h"), now)
  }


  avatar:string = "";

  chooseFromAlbum() {
    const options: ImagePickerOptions = {
      maximumImagesCount: 1,
      width: 800,
      height: 800,
      quality: 80
    };

// 获取图片
    this.imagePicker.getPictures(options).then((results) => {
      console.log('result1:', results);
      this.avatar = results[0];
    });
  }

  upload: any = {
    fileKey: 'introPhoto',//接收图片时的key
    fileName: 'imageName.jpg',
    headers: {
      'Accept': 'text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8',//不加入 发生错误！！
      'Authorization': this.currentUser.token
    },
    params: {}, //需要额外上传的参数
    success: (data) => {
    },//图片上传成功后的回调
    error: (err) => {
    },//图片上传失败后的回调
    listen: () => {
    }//监听上传过程
  };

  create() {
    //console.log(this.name, this.place, this.type, this.detail, this.userLimit, this.startTimeMinLimit, this.endTimeMinLimit);


    let options: any;
    options = {
      fileKey: this.upload.fileKey,
      headers: this.upload.headers,
      params: {'createActivityReq': {'name': this.name, 'place': this.place, 'detail': this.detail, 'type': this.type,
      'beginDate': this._startTime.format("YYYY-MM-DD HH:mm"), 'endDate': this._endTime.format("YYYY-MM-DD HH:mm"), 'userLimit': this.userLimit}}
    };
    this.fileTransfer.upload(this.avatar, 'http://54.245.150.228:7477/activities', options)
      .then((res) => {
        this.toast.create({message: '发布成功', duration: 1500}).present();
        //this.superTagController.slideTo(0);
    }, (err) => {
        this.toast.create({message: '发布失败', duration: 1500}).present();
      });

  }


  ionViewDidLoad() {
    console.log('ionViewDidLoad NewEventPage');
  }

  text = "";

  showCreateEvent() {
    this.button = 1;
    console.log(this.button);
  }

  showAddEvent() {
    this.button = 2;
    console.log(this.button);
  }

  input(ev: any) {
    this.text = ev.target.value;
  }

  clear(ev: any) {
    ev.target.value = '';
    this.text = '';
  }

  event = null;

  search() {
    console.log(this.text);
    this.eventService.getEventByInvite(this.text, this.currentUser.token).then(res => {
      if (res.success == false) {
        this.toast.create({ message: '请求错误', duration: 1500 }).present();
      }
      else {
        this.event = res;
      }
    });
  }

  joinEvent() {
    this.eventService.joinEvent(this.event.activityId, this.currentUser.token).then(res => {
      if (res.success == false) {
        this.toast.create({ message: '请求错误', duration: 1500 }).present();
      }
      else {
        this.toast.create({ message: '加入成功', duration: 1500 }).present();
      }
    });
  }

}
