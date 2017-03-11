package net.sgonzalez.freepicross.presentation.menus.activity;

import net.sgonzalez.freepicross.di.component.ActivityComponent;
import net.sgonzalez.freepicross.presentation.base.BaseActivity;

public class TitleActivity
extends BaseActivity {
  @Override
  protected void onInject(ActivityComponent activityComponent) {
    activityComponent.inject(this);
  }
}
