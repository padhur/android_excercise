package app.android.com.androidexcercise.newsFeed.view;

import app.android.com.androidexcercise.base.BaseMVPView;
import app.android.com.androidexcercise.newsFeed.model.NewsFeed;
import app.android.com.androidexcercise.newsFeed.model.Row;


public interface NewsFeedView extends BaseMVPView {
    void updateData(NewsFeed newsFeed);
    void updateData(String title,int size);
    void updateRowData(Row row);
}
