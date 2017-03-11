package net.sgonzalez.freepicross.presentation.base;

public abstract class BasePresenter<V extends BasePresenter.BaseView> {
  private V view;

  public void attachView(V view) {
    this.view = view;
  }

  public V getView() {
    if (view == null) {
      throw new IllegalStateException("view isn't attached. Did you invoke Presenter#attachView()?");
    }
    return view;
  }

  public void detachView() {
    view = null;
  }

  public interface BaseView {

  }
}
