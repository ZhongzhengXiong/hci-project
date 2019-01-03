import { Component } from '@angular/core';
import {
  IonicPage, LoadingController, NavController, NavParams, ToastController,
  ActionSheetController, AlertController, normalizeURL
} from 'ionic-angular';
import { AbstractControl, FormBuilder, FormControl, FormGroup, Validators } from "@angular/forms";

import { StartupPage } from "../startup/startup";
import {HttpService} from "../../providers/HttpService";
import {CurrentUserProvider} from "../../providers/CurrentUser";
import {LoginService} from "../../providers/LoginService";
import {ImagePicker, ImagePickerOptions} from "@ionic-native/image-picker";
import { FileTransfer, FileUploadOptions, FileTransferObject }from'@ionic-native/file-transfer';
import { File } from '@ionic-native/file';

@IonicPage()
@Component({
  selector: 'page-login',
  templateUrl: 'login.html',
})
export class LoginPage {

  private loginGroup: FormGroup;
  private registerGroup: FormGroup;

  private currentPage: 'login' | 'register' = "login";
  private firstTime: boolean = true;

  avatar: string = "";

  constructor(public navCtrl: NavController, public navParams: NavParams,
              private fb: FormBuilder,
              public actionSheetCtrl: ActionSheetController, public alertCtrl: AlertController, public imagePicker: ImagePicker,
              public currentUser: CurrentUserProvider,
              public loginService: LoginService,
              public toast: ToastController,
              private file:File,
              private fileTransfer:FileTransferObject
  ) {
    this.loginGroup = this.fb.group({
      tel: new FormControl('', [Validators.required, Validators.pattern('^[0-9]{1,}$')]),
      password: new FormControl('', [Validators.required])
    });
    this.registerGroup = this.fb.group({
      tel: new FormControl('', [Validators.required, Validators.pattern('^[0-9]{1,}$')]),
      email: new FormControl('', [Validators.required, Validators.email]),
      nickname: new FormControl('', [Validators.required]),
      password: new FormControl('', Validators.required),
      passwordRepeat: new FormControl('', Validators.required)
    }, {
      validator: (group: FormGroup): { [p: string]: boolean } | null => {
        return group.controls['password'].value === group.controls['passwordRepeat'].value ?
          null : { passwordsNotMatch: true }
      }
    })
  }

  ionViewDidLoad() {
    console.log('ionViewDidLoad LoginPage');
  }

  login() {
    const { tel, password } = this.loginGroup.value;
    this.loginService.login(tel, password).then(res => {
        console.log(res);
        if (res.token != null) {
          this.currentUser.set(res.user.name, res.user.id, res.token, res.user.avatar);
          this.gotoStartupPage();
        }
        else {
          this.toast.create({ message: '用户名或密码错误', duration: 1500 }).present();
        }
    })
  }

  upload: any = {
    fileKey: 'avatar',//接收图片时的key
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

  register() {
    const { tel, email, nickname, password, passwordRepeat } = this.registerGroup.value;
    let options: any;
    options = {
      fileKey: this.upload.fileKey,
      headers: this.upload.headers,
      params: {'registerReq': {'name': nickname, 'password': password, 'email': email, 'phone': tel}}
    };
    this.fileTransfer.upload(this.avatar, 'http://54.245.150.228:7477/users', options)
      .then((res) => {
        this.toast.create({message: '注册成功', duration: 1500}).present();
        this.currentPage = "login";
      }, (err) => {
        this.toast.create({message: '注册失败', duration: 1500}).present();
      });
  }

  gotoStartupPage() {
    return this.navCtrl.setRoot(StartupPage);
  }

  toggleCurrentPage() {
    this.firstTime = false;
    this.currentPage = this.currentPage === "login" ? "register" : "login";
  }

  private static formControlHasError(formControl: AbstractControl, error: string): boolean {
    return formControl.touched && formControl.hasError(error)
  }

  private loginError(group = 'login'): string {
    const control = name => (group === 'login' ? this.loginGroup : this.registerGroup).controls[name];
    if (LoginPage.formControlHasError(control('tel'), 'required'))
      return '请输入手机号';
    else if (LoginPage.formControlHasError(control('tel'), 'pattern'))
      return '请输入正确的手机号';
    else if (LoginPage.formControlHasError(control('password'), 'required'))
      return '请输入密码';
  }

  private registerError(group = 'register'): string {
    const control = name => (group === 'login' ? this.loginGroup : this.registerGroup).controls[name];
    if (LoginPage.formControlHasError(control('tel'), 'required'))
      return '请输入手机号';
    else if (LoginPage.formControlHasError(control('tel'), 'pattern'))
      return '请输入正确的手机号';
    else if (LoginPage.formControlHasError(control('email'), 'required'))
      return '请输入邮箱';
    else if (LoginPage.formControlHasError(control('email'), 'email'))
      return '请输入正确的邮箱';
    else if (LoginPage.formControlHasError(control('nickname'), 'required'))
      return '请输入昵称';
    else if (LoginPage.formControlHasError(control('password'), 'required'))
      return '请输入密码';
    if (LoginPage.formControlHasError(this.registerGroup, 'passwordsNotMatch'))
      return '两次输入的密码不一致'
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
    });
  }


}
