package net.sgonzalez.freepicross.presentation.menus.fragment;

import android.content.Context;
import butterknife.OnClick;
import javax.inject.Inject;
import net.sgonzalez.freepicross.R;
import net.sgonzalez.freepicross.di.component.ActivityComponent;
import net.sgonzalez.freepicross.domain.navigation.Navigator;
import net.sgonzalez.freepicross.presentation.base.BaseFragment;
import net.sgonzalez.freepicross.presentation.menus.presentation.TitlePresenter;

public class TitleFragment
extends BaseFragment
implements TitlePresenter.View {
  @Inject TitlePresenter presenter;
  @Inject Navigator navigator;
  private Callbacks callbacks;

  public static TitleFragment newInstance() {
    return new TitleFragment();
  }

  @Override
  public void onAttach(Context context) {
    super.onAttach(context);
    callbacks = (Callbacks) context;
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
    return R.layout.fragment_title;
  }

  @Override
  protected void onDetachView() {
    presenter.detachView();
  }

  @OnClick(R.id.start_game)
  public void onStartClicked() {
    presenter.onStartGameClicked();
  }

  @Override
  public void onStartGame() {
    callbacks.onStartGame(navigator);
  }

  public interface Callbacks {

    void onStartGame(Navigator navigator);
  }
}
