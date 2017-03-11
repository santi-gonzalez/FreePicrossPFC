package net.sgonzalez.freepicross.presentation.base;

import android.support.annotation.NonNull;
import android.view.ViewGroup;
import butterknife.BindView;
import net.sgonzalez.freepicross.R;

public abstract class BaseFullscreenActivity
extends BaseActivity {
  private static final String FRAGMENT = "FRAGMENT";
  @BindView(R.id.content) ViewGroup content;

  @Override
  protected final int getContentView() {
    return R.layout.activity_fullscreen;
  }

  @Override
  protected final void onCreateFragments() {
    getFragmentManager().beginTransaction().add(content.getId(), onCreateFullscreenFragment(), FRAGMENT).commit();
  }

  @NonNull
  protected <T extends BaseFragment> T getFullscreenFragment() {
    //noinspection unchecked
    return (T) getFragmentManager().findFragmentByTag(FRAGMENT);
  }

  @NonNull
  protected abstract BaseFragment onCreateFullscreenFragment();
}
