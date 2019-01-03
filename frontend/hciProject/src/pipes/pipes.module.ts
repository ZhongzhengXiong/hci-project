import { NgModule } from '@angular/core';
import { EventFilterPipe } from './event-filter/event-filter';
@NgModule({
	declarations: [EventFilterPipe],
	imports: [],
	exports: [EventFilterPipe]
})
export class PipesModule {}
