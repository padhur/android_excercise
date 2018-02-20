package app.android.com.androidexcercise.app;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import app.android.com.androidexcercise.newsFeed.view.NewsFeedActivity;

public class MainActivity extends AppCompatActivity {

    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((AEApplication) getApplicationContext()).getAppComponent().inject(this);
        startNewsFeedActivity();
    }

    private void startNewsFeedActivity() {
        this.intent = new Intent(this, NewsFeedActivity.class);
        startActivity(this.intent);
        finish();
    }
}
