package net.sgonzalez.freepicross.di.component;

import dagger.Component;
import net.sgonzalez.freepicross.di.module.ActivityModule;
import net.sgonzalez.freepicross.di.scope.ActivityScope;
import net.sgonzalez.freepicross.presentation.game.activity.GameActivity;
import net.sgonzalez.freepicross.presentation.menus.activity.TitleActivity;

@ActivityScope
@Component(dependencies = ApplicationComponent.class, modules = { ActivityModule.class })
public interface ActivityComponent {
  void inject(GameActivity gameActivity);

  void inject(TitleActivity titleActivity);
}
