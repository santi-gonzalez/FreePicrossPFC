package net.sgonzalez.freepicross.presentation.base;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.ButterKnife;
import net.sgonzalez.freepicross.App;
import net.sgonzalez.freepicross.di.component.ActivityComponent;
import net.sgonzalez.freepicross.di.component.ApplicationComponent;
import net.sgonzalez.freepicross.di.component.DaggerActivityComponent;
import net.sgonzalez.freepicross.di.module.ActivityModule;

public abstract class BaseFragment
extends Fragment {
  public static final int INVALID_ID = 0;

  @Override
  public void onAttach(Context context) {
    super.onAttach(context);
    injectDependencies();
    onAttachView();
  }

  private void injectDependencies() {
    onInject(DaggerActivityComponent.builder()
                                    .applicationComponent(getApplicationComponent())
                                    .activityModule(new ActivityModule(getBaseActivity()))
                                    .build());
  }

  protected abstract void onInject(ActivityComponent activityComponent);

  /** Presenter view should be attached ({@code presenter.attachView(this)}). */
  protected abstract void onAttachView();

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
    View view = null;
    int contentView = getContentView();
    if (contentView > INVALID_ID) {
      view = inflater.inflate(contentView, container, false);
    }
    return view;
  }

  protected abstract int getContentView();

  @Override
  public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    bindViews();
  }

  private void bindViews() {
    View view = getView();
    if (view != null) {
      ButterKnife.bind(this, view);
    }
  }

  @Override
  public void onDetach() {
    super.onDetach();
    onDetachView();
  }

  /** Presenter view should be detached ({@code presenter.detachView(this)}). */
  protected abstract void onDetachView();

  protected App getApp() {
    return getBaseActivity().getApp();
  }

  protected ApplicationComponent getApplicationComponent() {
    return getApp().getApplicationComponent();
  }

  protected BaseActivity getBaseActivity() {
    return (BaseActivity) getActivity();
  }
}
