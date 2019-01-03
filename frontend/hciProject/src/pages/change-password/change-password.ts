import {Component} from '@angular/core';
import {
  IonicPage, NavController, NavParams, ActionSheetController, AlertController,
  ToastController, App
} from 'ionic-angular';
import {FormGroup, FormBuilder, FormControl, Validators, AbstractControl} from "@angular/forms";
import {CurrentUserProvider} from "../../providers/CurrentUser";
import {LoginService} from "../../providers/LoginService";
import {StartupPage} from "../startup/startup";
import {PersonalPage} from "../personal/personal";

/**
 * Generated class for the ChangePasswordPage page.
 *
 * See https://ionicframework.com/docs/components/#navigation for more info on
 * Ionic pages and navigation.
 */

@IonicPage()
@Component({
  selector: 'page-change-password',
  templateUrl: 'change-password.html',
})
export class ChangePasswordPage {

  private changeGroup: FormGroup;

  constructor(public navCtrl: NavController, public navParams: NavParams,
              private appCtrl: App,
              private fb: FormBuilder,
              public actionSheetCtrl: ActionSheetController, public alertCtrl: AlertController,
              public currentUser: CurrentUserProvider,
              public loginService: LoginService,
              public toast: ToastController) {
    this.changeGroup = this.fb.group({
      oldPassword: new FormControl('', Validators.required),
      newPassword: new FormControl('', Validators.required),
      passwordRepeat: new FormControl('', Validators.required)
    }, {
      validator: (group: FormGroup): {[p: string]: boolean} | null => {
        return group.controls['newPassword'].value === group.controls['passwordRepeat'].value ?
          null : {passwordsNotMatch: true}
      }
    })
  }

  ionViewDidLoad() {
    console.log('ionViewDidLoad ChangePasswordPage');
  }

  change() {
    const {oldPassword, newPassword, passwordRepeated} = this.changeGroup.value;
    this.loginService.changePassword(oldPassword, newPassword, this.currentUser.token).then(res => {
      console.log(res);
      if (res.success == false) {
        this.toast.create({ message: '请求错误', duration: 1500 }).present();
      }
      else {
        if (res.status == "Success") {
          this.toast.create({message: '修改成功', duration: 1500}).present();
        }
        else {
          this.toast.create({message: '修改失败', duration: 1500}).present();
        }
      }
    })
    this.appCtrl.goBack();
  }

  private static formControlHasError(formControl: AbstractControl, error: string): boolean {
    return formControl.touched && formControl.hasError(error)
  }

  private changeError(): string {
    const control = name => (this.changeGroup).controls[name];
    if (ChangePasswordPage.formControlHasError(control('oldPassword'), 'required'))
      return '请输入旧密码';
    else if (ChangePasswordPage.formControlHasError(control('newPassword'), 'pattern'))
      return '请输入新密码';
    if (ChangePasswordPage.formControlHasError(this.changeGroup, 'passwordsNotMatch'))
      return '两次输入的密码不一致'
  }
}
