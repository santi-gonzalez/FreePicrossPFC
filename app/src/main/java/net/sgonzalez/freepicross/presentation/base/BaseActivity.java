package net.sgonzalez.freepicross.presentation.base;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import butterknife.ButterKnife;
import net.sgonzalez.freepicross.App;

public abstract class BaseActivity
extends AppCompatActivity {
  public static final int INVALID_ID = BaseFragment.INVALID_ID;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    initContentView();
    bindViews();
    onCreateFragments();
  }

  private void initContentView() {
    int contentView = getContentView();
    if (contentView > INVALID_ID) {
      setContentView(contentView);
    }
  }

  private void bindViews() {
    ButterKnife.bind(this);
  }

  protected App getApp() {
    return (App) getApplication();
  }

  @LayoutRes
  protected abstract int getContentView();

  protected abstract void onCreateFragments();
}
