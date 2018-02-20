package app.android.com.androidexcercise.newsFeed.presenter;


import app.android.com.androidexcercise.app.dagger.IOScheduler;
import app.android.com.androidexcercise.app.dagger.MainScheduler;
import app.android.com.androidexcercise.base.BaseMVPPresenter;
import app.android.com.androidexcercise.newsFeed.model.NewsFeed;
import app.android.com.androidexcercise.newsFeed.service.NewsFeedService;
import app.android.com.androidexcercise.newsFeed.view.NewsFeedView;
import app.android.com.androidexcercise.utils.RetrofitHelper;
import io.reactivex.Scheduler;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;


public class NewsFeedPresenter extends BaseMVPPresenter<NewsFeedView> {

    @MainScheduler
    Scheduler mainThreadScheduler;
    @IOScheduler
    Scheduler ioThreadScheduler;
    //To simulate the lazy load behaviour
    private NewsFeed newsFeedData;

    public NewsFeedPresenter(Scheduler mainThreadScheduler, Scheduler ioThreadScheduler) {
        this.mainThreadScheduler = mainThreadScheduler;
        this.ioThreadScheduler = ioThreadScheduler;
    }

    /**
     * Method to get the Date from the Webservice
     */
    public void getNewsFeedData() {
        getView().showProgressDialog();
        NewsFeedService newsFeedService = (NewsFeedService) RetrofitHelper
                .getEndpointInstance(NewsFeedService.class);
        Disposable disposable = newsFeedService.getData().observeOn(mainThreadScheduler)
                .subscribeOn(ioThreadScheduler).subscribeWith(new DisposableObserver<NewsFeed>() {
                    @Override
                    public void onNext(NewsFeed newsFeed) {
                        getView().hideProgressDialog();
                        newsFeedData = newsFeed;
                        //getView().updateData(newsFeedData.getTitle(),newsFeedData.getRows().size());
                        getView().updateData(newsFeedData);
                    }

                    @Override
                    public void onError(Throwable e) {
                        getView().hideProgressDialog();
                    }

                    @Override
                    public void onComplete() {
                        getView().hideProgressDialog();
                    }
                });
        getCompositeDisposable().add(disposable);
    }
    /**
     * Method to load Row data Lazily
     */
    public void getRowData(int index) {
        if(index > newsFeedData.getRows().size()) {
            getView().updateRowData(newsFeedData.getRows().get(index));
        } else {
            getView().updateRowData(null);
        }
    }
}
