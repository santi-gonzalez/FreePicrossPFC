package net.sgonzalez.freepicross.di.module;

import dagger.Module;
import dagger.Provides;
import net.sgonzalez.freepicross.di.scope.ActivityScope;
import net.sgonzalez.freepicross.presentation.base.BaseActivity;

@Module
public class ActivityModule {

  private final BaseActivity activity;

  public ActivityModule(BaseActivity activity) {
    this.activity = activity;
  }

  @Provides
  @ActivityScope
  BaseActivity getActivity() {
    return activity;
  }
}
