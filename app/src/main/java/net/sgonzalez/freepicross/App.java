package net.sgonzalez.freepicross;

import android.app.Application;
import net.sgonzalez.freepicross.di.component.ApplicationComponent;
import net.sgonzalez.freepicross.di.component.DaggerApplicationComponent;
import net.sgonzalez.freepicross.di.module.ApplicationModule;

public class App
extends Application {
  private static App sApp;
  private ApplicationComponent applicationComponent;

  public static App getApp() {
    return sApp;
  }

  @Override
  public void onCreate() {
    super.onCreate();
    registerApplicationInstance();
    createApplicationComponent();
    injectDependencies();
  }

  public ApplicationComponent getApplicationComponent() {
    return applicationComponent;
  }

  private void registerApplicationInstance() {
    sApp = this;
  }

  private void createApplicationComponent() {
    applicationComponent = DaggerApplicationComponent.builder().applicationModule(new ApplicationModule(this)).build();
  }

  private void injectDependencies() {
    applicationComponent.inject(this);
  }
}
