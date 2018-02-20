package app.android.com.androidexcercise.app.dagger;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Qualifier;


@Documented
@Retention(RetentionPolicy.RUNTIME)
@Qualifier
public @interface MainScheduler {
}
