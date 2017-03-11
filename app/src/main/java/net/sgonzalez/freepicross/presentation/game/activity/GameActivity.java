package net.sgonzalez.freepicross.presentation.game.activity;

import net.sgonzalez.freepicross.di.component.ActivityComponent;
import net.sgonzalez.freepicross.presentation.base.BaseActivity;

public class GameActivity
extends BaseActivity {
  @Override
  protected void onInject(ActivityComponent activityComponent) {
    activityComponent.inject(this);
  }
}
