package net.sgonzalez.freepicross.di.module;

import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;
import net.sgonzalez.freepicross.App;

@Module
public class ApplicationModule {
  private final App app;

  public ApplicationModule(App app) {
    this.app = app;
  }

  @Provides
  @Singleton
  public App getApp() {
    return app;
  }
}
