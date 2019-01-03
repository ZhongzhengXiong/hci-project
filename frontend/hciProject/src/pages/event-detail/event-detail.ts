import {Component} from '@angular/core';
import {
  IonicPage, NavController, NavParams, App, ToastController, ActionSheetController,
  AlertController, LoadingController
} from 'ionic-angular';
import * as moment from 'moment';
import {CurrentUserProvider} from "../../providers/CurrentUser";
import {EventService} from "../../providers/EventService";
import {LoginService} from "../../providers/LoginService";
import {ImagePicker, ImagePickerOptions} from "@ionic-native/image-picker";
import { FileTransfer, FileUploadOptions, FileTransferObject }from'@ionic-native/file-transfer';
import { File } from '@ionic-native/file';

/**
 * Generated class for the EventDetailPage page.
 *
 * See https://ionicframework.com/docs/components/#navigation for more info on
 * Ionic pages and navigation.
 */

@IonicPage()
@Component({
  selector: 'page-event-detail',
  templateUrl: 'event-detail.html',
})
export class EventDetailPage {
  upload: any = {
    fileKey: 'file',//接收图片时的key
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


  detail = null;
  picture = null;
  comment = null;
  noti = null;
  button = 0;
  activityId = null;
  newComment = "";
  newNotiTitle = "";
  newNotiContent = "";

  avatar = "";

  constructor(public navCtrl: NavController, public navParams: NavParams,
              public actionSheetCtrl: ActionSheetController,
              public alertCtrl: AlertController,
              public currentUser: CurrentUserProvider,
              public eventService: EventService,
              public loginService: LoginService,
              public toast: ToastController,
              private imagePicker: ImagePicker,
              private file:File,
              private fileTransfer:FileTransferObject) {
    this.activityId = this.navParams.get('activityId');
    this.refresh();
  }

  formatTime = (utcTime) => {
    return moment(utcTime).format('YYYY-MM-DD HH:mm')
  };

  ionViewDidLoad() {
    console.log('ionViewDidLoad EventDetailPage');
  }

  showDetail() {
    this.button = 1;
  }

  showPicture() {
    this.button = 2;
  }

  showComment() {
    this.button = 3;
  }

  showNoti() {
    this.button = 4;
  }

  uploadPicture() {
    this.imagePicker.getPictures({maximumImagesCount: 1}).then(([result]) => {
      if (result) {
        this.eventService.uploadPicture(this.activityId, result, this.currentUser.token).then(res => {
          console.log(res);
          if (res.success == false) {
            this.toast.create({message: '网络错误', duration: 1500}).present();
          }
          else {
            this.toast.create({message: '上传成功', duration: 1500}).present();
            this.refresh();
          }
        })
      }
    })
  }

  uploadComment() {
    this.eventService.addComment(this.activityId, this.newComment, this.currentUser.token).then(res => {
      console.log(res);
      if (res.success == false) {
        this.toast.create({message: '请求错误', duration: 1500}).present();
      }
      else {
        this.toast.create({message: '评论成功', duration: 1500}).present();
        this.refresh();
      }
    });
  }

  uploadNoti() {
    this.eventService.addNoti(this.activityId, this.newNotiTitle, this.newNotiContent, this.currentUser.token).then(res => {
      console.log(res);
      if (res.success == false) {
        this.toast.create({message: '请求错误', duration: 1500}).present();
      }
      else {
        this.toast.create({message: '发布成功', duration: 1500}).present();
        this.refresh();
      }
    });
  }

  test() {
    console.log(this.currentUser.id);
    console.log(this.detail.activityId);
    console.log(this.currentUser.id == this.detail.activityId);
  }

  refresh() {
    if (this.button == 0 || this.button == 1) {
      this.eventService.getEventById(this.activityId, this.currentUser.token).then(res => {
        console.log(res);
        if (res.success == false) {
          this.toast.create({message: '请求错误', duration: 1500}).present();
        }
        else {
          this.detail = res;
        }
      });
    }
    if (this.button == 0 || this.button == 2) {
      this.eventService.getPictureById(this.activityId, this.currentUser.token).then(res => {
        console.log(res);
        if (res.success == false) {
          this.toast.create({message: '请求错误', duration: 1500}).present();
        }
        else {
          this.picture = res;
        }
      });
    }
    if (this.button == 0 || this.button == 3) {
      this.eventService.getCommentById(this.activityId, this.currentUser.token).then(res => {
        console.log(res);
        if (res.success == false) {
          this.toast.create({message: '请求错误', duration: 1500}).present();
        }
        else {
          this.comment = res;
        }
      });
    }
    if (this.button == 0 || this.button == 4) {
      this.eventService.getNotiById(this.activityId, this.currentUser.token).then(res => {
        console.log(res);
        if (res.success == false) {
          this.toast.create({message: '请求错误', duration: 1500}).present();
        }
        else {
          this.noti = res;
        }
      });
    }
    if (this.button == 0) {
      this.button = 1;
    }
  }

  doRefresh(refresher) {
    this.detail = null;
    this.picture = null;
    this.comment = null;
    this.avatar = "";

    setTimeout(() => {
      refresher.complete();
    }, 2000)
    this.refresh();
  }


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
      this.uploadImage();
    });
  }

  public uploadImage() {
    let options: any;
    options = {
      fileKey: this.upload.fileKey,
      headers: this.upload.headers,
      params: this.upload.params
    };
    this.fileTransfer.upload(this.avatar, 'http://54.245.150.228:7477/activities/' + this.activityId + '/photos', options)
      .then((data) => {
        this.toast.create({message: '上传成功', duration: 1500}).present();
      }, (err) => {
        this.toast.create({message: '上传失败', duration: 1500}).present();
      });
  }
}
