package net.sgonzalez.freepicross.presentation.menus.presentation;

import javax.inject.Inject;
import net.sgonzalez.freepicross.di.scope.ActivityScope;
import net.sgonzalez.freepicross.presentation.base.BasePresenter;

@ActivityScope
public class TitlePresenter
extends BasePresenter<TitlePresenter.View> {

  @Inject
  public TitlePresenter() {
  }

  public void onStartGameClicked() {
    getView().onStartGame();
  }

  public interface View
  extends BasePresenter.BaseView {

    void onStartGame();
  }
}
