package net.sgonzalez.freepicross.di.component;

import dagger.Component;
import javax.inject.Singleton;
import net.sgonzalez.freepicross.App;
import net.sgonzalez.freepicross.di.module.ApplicationModule;

@Singleton
@Component(modules = { ApplicationModule.class })
public interface ApplicationComponent {
  App getApp();
}
