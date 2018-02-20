package app.android.com.androidexcercise.app.dagger;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;


@Scope
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface SingleInstanceIn {
    Class<?> value();
}

