package app.android.com.androidexcercise.utils;

import android.util.Log;
import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import java.util.concurrent.TimeUnit;
import app.android.com.androidexcercise.BuildConfig;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class RetrofitHelper {

    private static OkHttpClient client;
    private static Retrofit retrofit;

    /**
     * Method to get the Retrofit instance which is used to generate the requester object
     *
     * @return Retrofit
     */
    public static Retrofit getRetrofitInstance() {

        if (client == null) {
            Log.i("SESSION", "Client Object null");
            OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient.Builder()
                    .connectTimeout(45, TimeUnit.SECONDS)
                    .readTimeout(45, TimeUnit.SECONDS)
                    .writeTimeout(15, TimeUnit.SECONDS);


            if (BuildConfig.DEBUG) {
                okHttpClientBuilder.addNetworkInterceptor(new StethoInterceptor());
            }
            client = okHttpClientBuilder.build();

        }

        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(Constants.URL)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();

        }
        return retrofit;
    }

    /**
     * Method to get the Retrofit instance
     * @param type
     * @return
     */
    public static Object getEndpointInstance(Class type) {
        Retrofit retrofit = getRetrofitInstance();

        return retrofit.create(type);
    }

    /**
     * Method to clear the instance
     */
    public static void reset() {
        client = null;
        retrofit = null;
    }

    /**
     * Method to cancel all requests
     */
    public static void cancelAllRequests() {
        if (client != null) {
            client.dispatcher().cancelAll();
        }
    }
}
