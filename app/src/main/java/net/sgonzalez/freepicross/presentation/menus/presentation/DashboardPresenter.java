package net.sgonzalez.freepicross.presentation.menus.presentation;

import javax.inject.Inject;
import net.sgonzalez.freepicross.presentation.base.BasePresenter;

public class DashboardPresenter
extends BasePresenter<DashboardPresenter.View> {

  @Inject
  public DashboardPresenter() {
  }

  public interface View
  extends BasePresenter.BaseView {

  }
}
