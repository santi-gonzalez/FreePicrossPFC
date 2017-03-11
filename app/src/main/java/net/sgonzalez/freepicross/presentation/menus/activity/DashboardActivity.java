package net.sgonzalez.freepicross.presentation.menus.activity;

import android.support.annotation.NonNull;
import net.sgonzalez.freepicross.presentation.base.BaseFragment;
import net.sgonzalez.freepicross.presentation.base.BaseFullscreenActivity;
import net.sgonzalez.freepicross.presentation.menus.fragment.DashboardFragment;

public class DashboardActivity
extends BaseFullscreenActivity {

  @NonNull
  @Override
  protected BaseFragment onCreateFullscreenFragment() {
    return DashboardFragment.newInstance();
  }
}
