package net.sgonzalez.freepicross.di.component;

import dagger.Component;
import javax.inject.Singleton;
import net.sgonzalez.freepicross.App;
import net.sgonzalez.freepicross.di.module.ApplicationModule;
import net.sgonzalez.freepicross.domain.navigation.Navigator;

@Singleton
@Component(modules = { ApplicationModule.class })
public interface ApplicationComponent {
  void inject(App app);
  App getApp();
  Navigator getNavigator();
}
