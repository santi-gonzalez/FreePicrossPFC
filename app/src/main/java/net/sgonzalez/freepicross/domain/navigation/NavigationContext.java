package net.sgonzalez.freepicross.domain.navigation;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

public class NavigationContext {
  private final Context context;

  private NavigationContext(Context context) {
    this.context = context;
  }

  public static NavigationContext from(Activity activity) {
    return new NavigationContext(activity);
  }

  public void startActivity(Class clazz) {
    startActivity(clazz, null);
  }

  public void startActivity(Class clazz, @Nullable Bundle extras) {
    Intent intent = new Intent(context, clazz);
    if (extras != null) {
      intent.putExtras(extras);
    }
    context.startActivity(intent);
  }
}
