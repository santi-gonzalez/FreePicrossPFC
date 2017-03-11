package net.sgonzalez.freepicross.di.component;

import dagger.Component;
import net.sgonzalez.freepicross.di.module.ActivityModule;
import net.sgonzalez.freepicross.di.scope.ActivityScope;
import net.sgonzalez.freepicross.presentation.game.fragment.GameFragment;
import net.sgonzalez.freepicross.presentation.menus.fragment.DashboardFragment;
import net.sgonzalez.freepicross.presentation.menus.fragment.TitleFragment;

@ActivityScope
@Component(dependencies = ApplicationComponent.class, modules = { ActivityModule.class })
public interface ActivityComponent {

  void inject(TitleFragment fragment);

  void inject(DashboardFragment fragment);

  void inject(GameFragment fragment);
}
