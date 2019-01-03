import { NgModule } from '@angular/core';
import { StepperComponent } from './stepper/stepper';
import { IonicModule } from "ionic-angular";
@NgModule({
	declarations: [StepperComponent],
	imports: [IonicModule],
	exports: [StepperComponent]
})
export class ComponentsModule {}
