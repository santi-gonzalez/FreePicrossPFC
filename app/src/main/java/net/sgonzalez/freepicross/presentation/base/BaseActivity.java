package net.sgonzalez.freepicross.presentation.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import net.sgonzalez.freepicross.App;
import net.sgonzalez.freepicross.di.component.ActivityComponent;
import net.sgonzalez.freepicross.di.component.ApplicationComponent;
import net.sgonzalez.freepicross.di.component.DaggerActivityComponent;
import net.sgonzalez.freepicross.di.module.ActivityModule;

public abstract class BaseActivity
extends AppCompatActivity {

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    injectDependencies();
  }

  private void injectDependencies() {
    onInject(DaggerActivityComponent.builder()
                                    .applicationComponent(getApplicationComponent())
                                    .activityModule(new ActivityModule(this))
                                    .build());
  }

  protected App getApp() {
    return (App) getApplication();
  }

  protected ApplicationComponent getApplicationComponent() {
    return getApp().getApplicationComponent();
  }

  protected abstract void onInject(ActivityComponent activityComponent);
}
