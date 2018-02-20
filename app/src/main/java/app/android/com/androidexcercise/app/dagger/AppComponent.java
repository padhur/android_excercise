package app.android.com.androidexcercise.app.dagger;

import app.android.com.androidexcercise.app.MainActivity;
import app.android.com.androidexcercise.newsFeed.view.NewsFeedActivity;
import dagger.Component;
import io.reactivex.Scheduler;
import retrofit2.Retrofit;


@SingleInstanceIn(AppComponent.class)
@Component(modules = {AppModule.class})
public interface AppComponent {

    void inject(MainActivity mainActivity);
    void inject(NewsFeedActivity newsFeedActivity);

    @IOScheduler
    Scheduler getIOScheduler();

    @MainScheduler
    Scheduler getMainScheduler();
}
