package app.android.com.androidexcercise.newsFeed.inject;

import app.android.com.androidexcercise.app.dagger.AppComponent;
import app.android.com.androidexcercise.app.dagger.SingleInstanceIn;
import app.android.com.androidexcercise.newsFeed.presenter.NewsFeedPresenter;
import app.android.com.androidexcercise.newsFeed.view.NewsFeedActivity;
import dagger.Component;


@SingleInstanceIn(NewsFeedComponent.class)
@Component(modules = NewsFeedModule.class, dependencies = AppComponent.class)
public interface NewsFeedComponent {

    void inject(NewsFeedActivity newsFeedActivity);
    NewsFeedPresenter getNewsFeedPresenter();
}
