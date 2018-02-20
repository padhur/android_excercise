package app.android.com.androidexcercise.newsFeed.inject;

import app.android.com.androidexcercise.app.dagger.IOScheduler;
import app.android.com.androidexcercise.app.dagger.MainScheduler;
import app.android.com.androidexcercise.app.dagger.SingleInstanceIn;
import app.android.com.androidexcercise.newsFeed.presenter.NewsFeedPresenter;
import dagger.Module;
import dagger.Provides;
import io.reactivex.Scheduler;


@Module
public class NewsFeedModule {

    @Provides
    @SingleInstanceIn(NewsFeedComponent.class)
    public NewsFeedPresenter provideNewsFeedPresenter(@MainScheduler Scheduler mainThreadScheduler, @IOScheduler Scheduler ioThreadScheduler) {
        return new NewsFeedPresenter(mainThreadScheduler,ioThreadScheduler);
    }
}
