import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EditgameComponent } from './editgame.component';

describe('EditgameComponent', () => {
  let component: EditgameComponent;
  let fixture: ComponentFixture<EditgameComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [EditgameComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(EditgameComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
