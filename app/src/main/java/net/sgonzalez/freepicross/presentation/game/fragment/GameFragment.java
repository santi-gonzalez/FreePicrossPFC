package net.sgonzalez.freepicross.presentation.game.fragment;

import net.sgonzalez.freepicross.di.component.ActivityComponent;
import net.sgonzalez.freepicross.presentation.base.BaseFragment;

public class GameFragment
extends BaseFragment {
  @Override
  protected void onInject(ActivityComponent activityComponent) {
    activityComponent.inject(this);
  }

  @Override
  protected void onAttachView() {
  }

  @Override
  protected int getContentView() {
    return 0;
  }

  @Override
  protected void onDetachView() {
  }
}
