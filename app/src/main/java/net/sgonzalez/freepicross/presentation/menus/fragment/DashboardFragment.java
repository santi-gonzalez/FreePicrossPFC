package net.sgonzalez.freepicross.presentation.menus.fragment;

import javax.inject.Inject;
import net.sgonzalez.freepicross.R;
import net.sgonzalez.freepicross.di.component.ActivityComponent;
import net.sgonzalez.freepicross.presentation.base.BaseFragment;
import net.sgonzalez.freepicross.presentation.menus.presentation.DashboardPresenter;

public class DashboardFragment
extends BaseFragment
implements DashboardPresenter.View {
  @Inject DashboardPresenter presenter;

  public static DashboardFragment newInstance() {
    return new DashboardFragment();
  }

  @Override
  protected void onInject(ActivityComponent activityComponent) {
    activityComponent.inject(this);
  }

  @Override
  protected void onAttachView() {
    presenter.attachView(this);
  }

  @Override
  protected int getContentView() {
    return R.layout.fragment_dashboard;
  }

  @Override
  protected void onDetachView() {
    presenter.detachView();
  }
}
