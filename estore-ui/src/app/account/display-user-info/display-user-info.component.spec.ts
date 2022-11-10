import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DisplayUserInfoComponent } from './display-user-info.component';

describe('DisplayUserInfoComponent', () => {
  let component: DisplayUserInfoComponent;
  let fixture: ComponentFixture<DisplayUserInfoComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ DisplayUserInfoComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(DisplayUserInfoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
