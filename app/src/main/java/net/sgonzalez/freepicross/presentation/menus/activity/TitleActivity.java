package net.sgonzalez.freepicross.presentation.menus.activity;

import android.support.annotation.NonNull;
import net.sgonzalez.freepicross.domain.navigation.NavigationContext;
import net.sgonzalez.freepicross.domain.navigation.Navigator;
import net.sgonzalez.freepicross.presentation.base.BaseFragment;
import net.sgonzalez.freepicross.presentation.base.BaseFullscreenActivity;
import net.sgonzalez.freepicross.presentation.menus.fragment.TitleFragment;

public class TitleActivity
extends BaseFullscreenActivity
implements TitleFragment.Callbacks {

  @NonNull
  @Override
  protected BaseFragment onCreateFullscreenFragment() {
    return TitleFragment.newInstance();
  }

  @Override
  public void onStartGame(Navigator navigator) {
    navigator.navigateToDashboard(NavigationContext.from(this));
  }
}
