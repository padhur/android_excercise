package app.android.com.androidexcercise.app;

import android.app.Application;
import com.facebook.stetho.Stetho;
import app.android.com.androidexcercise.BuildConfig;
import app.android.com.androidexcercise.app.dagger.AppComponent;
import app.android.com.androidexcercise.app.dagger.AppModule;
import app.android.com.androidexcercise.app.dagger.DaggerAppComponent;


public class AEApplication extends Application {

    protected AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        if(BuildConfig.DEBUG) {
            Stetho.initializeWithDefaults(this);
        }
        appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .build();
    }

    public AppComponent getAppComponent(){
        return this.appComponent;
    }
}
