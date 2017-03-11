package net.sgonzalez.freepicross.domain.navigation;

import javax.inject.Inject;
import javax.inject.Singleton;
import net.sgonzalez.freepicross.presentation.menus.activity.DashboardActivity;

@Singleton
public class Navigator {

  @Inject
  public Navigator() {
  }

  public void navigateToDashboard(NavigationContext context) {
    context.startActivity(DashboardActivity.class);
  }
}
