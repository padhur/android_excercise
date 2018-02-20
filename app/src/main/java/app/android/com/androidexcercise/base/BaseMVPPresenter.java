package app.android.com.androidexcercise.base;

import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter;
import io.reactivex.disposables.CompositeDisposable;


public abstract class BaseMVPPresenter<V extends BaseMVPView> extends MvpBasePresenter<V> {

    private final CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Override
    public void attachView(V view) {
        super.attachView(view);
    }

    @Override
    public void detachView(boolean retainInstance) {
        super.detachView(retainInstance);
        if(!retainInstance) {
            compositeDisposable.clear();
            compositeDisposable.dispose();
        }
    }

    public CompositeDisposable getCompositeDisposable() {
        return compositeDisposable;
    }
}
