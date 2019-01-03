import { Injectable } from '@angular/core';
import { LoadingController, ToastController } from "ionic-angular";
import { Observable, Subject } from "rxjs";
import { zip } from "rxjs/observable/zip";

/*
 Generated class for the LoadingCoverProvider provider.

 See https://angular.io/guide/dependency-injection for more info on providers
 and Angular DI.
 */
@Injectable()
export class LoadingCoverProvider {

  constructor(public loading: LoadingController,
              private toast: ToastController,) {
    console.log('Hello LoadingCoverProvider Provider');
  }

  fetchData(...ob: Observable<any>[]): Observable<any>[] {
    const subjects = ob.map(() => new Subject());
    const loadingView = this.loading.create({ content: '加载中……' });
    loadingView.present();
    zip.apply(null, ob).subscribe(
      data => {
        loadingView.dismiss();
        data.forEach((v, idx) => {
          console.log(v, idx);
          subjects[idx].next(v);
          subjects[idx].complete()
        })
      }, (err) => {
        loadingView.dismiss();
        subjects.forEach((s) => {
          s.error(err);
          s.complete()
        });
        this.toast.create({ message: '网络错误', duration: 1500 }).present()
      });
    return subjects;
  }

}
