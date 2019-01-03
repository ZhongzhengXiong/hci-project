import { Component, EventEmitter, Input, Output, ViewChild } from '@angular/core';
import { TextInput } from "ionic-angular";

/**
 * Generated class for the StepperComponent component.
 *
 * See https://angular.io/api/core/Component for more info on Angular
 * Components.
 */
@Component({
  selector: 'stepper',
  templateUrl: 'stepper.html'
})
export class StepperComponent {

  private _value: number = 0;
  @Output() valueChange = new EventEmitter<number>();

  @Input() enabled: boolean = true;
  @Input() maxValue: number | null = null;
  @Input() minValue: number | null = null;

  @ViewChild(TextInput) inputBox: TextInput;

  constructor() {
    console.log('Hello StepperComponent Component');
  }

  isLegal(v) {
    return (this.minValue === null || this.minValue <= v) &&
      (this.maxValue === null || this.maxValue >= v) &&
      v === parseInt(v)
  }

  @Input() set value(v) {
    if (!isNaN(v) && this.isLegal(+v)) {
      v = +v;
      if (this._value !== v) {
        this._value = v;
        this.valueChange.emit(v);
      }
    }
  }

  get value() {
    return this._value
  }

  increment() {
    this.value += 1
  }

  decrement() {
    this.value -= 1
  }

  blur() {
    if (this.inputBox.value != this.value.toString()) {
      this.inputBox.setValue(this.value)
    }
  }

}
