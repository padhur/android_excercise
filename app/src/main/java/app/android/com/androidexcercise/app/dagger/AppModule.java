package app.android.com.androidexcercise.app.dagger;

import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.util.concurrent.TimeUnit;

import app.android.com.androidexcercise.BuildConfig;
import app.android.com.androidexcercise.app.AEApplication;
import dagger.Module;
import dagger.Provides;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


@Module
public class AppModule {
    private final AEApplication aeApplication;

    public AppModule(AEApplication aeApplication) {
        this.aeApplication = aeApplication;
    }

    @Provides
    @SingleInstanceIn(AppComponent.class)
    OkHttpClient provideOkHttpClient() {
        if(BuildConfig.DEBUG) {
            OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient.Builder()
                    .addNetworkInterceptor(new StethoInterceptor())
                    .connectTimeout(15, TimeUnit.SECONDS)
                    .readTimeout(15, TimeUnit.SECONDS)
                    .writeTimeout(15, TimeUnit.SECONDS);
            return okHttpClientBuilder.build();
        }else{
            OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient.Builder()
                    .addNetworkInterceptor(new StethoInterceptor())
                    .connectTimeout(15, TimeUnit.SECONDS)
                    .readTimeout(15, TimeUnit.SECONDS)
                    .writeTimeout(15, TimeUnit.SECONDS);
            return okHttpClientBuilder.build();
        }
    }

    @Provides
    @SingleInstanceIn(AppComponent.class)
    Retrofit provideRetrofit2(OkHttpClient okHttpClient) {
        Gson gson = new GsonBuilder()
                .setLenient()
                .setPrettyPrinting()
                .create();
        return new Retrofit.Builder()
                .baseUrl("")
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    @IOScheduler
    @Provides
    @SingleInstanceIn(AppComponent.class)
    Scheduler provideIOScheduler() {
        return Schedulers.io();
    }

    @MainScheduler
    @Provides
    @SingleInstanceIn(AppComponent.class)
    Scheduler provideMainScheduler() {
        return AndroidSchedulers.mainThread();
    }
}
