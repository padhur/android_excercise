package app.android.com.androidexcercise.newsFeed.view;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.Size;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;

import java.util.ArrayList;
import java.util.List;

import app.android.com.androidexcercise.R;
import app.android.com.androidexcercise.newsFeed.model.NewsFeed;
import app.android.com.androidexcercise.newsFeed.model.Row;
import app.android.com.androidexcercise.utils.Constants;
import butterknife.BindView;
import butterknife.ButterKnife;

public class NewsFeedRecycleViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final NewsFeed newsFeed;
    private final Context context;
    private int count;
    private OnLoadMoreDataListener loadMoreDataListener;
    boolean isLoading = false;
    boolean isMoreDataAvailable = true;
    String type;
    private final int NEWSFEED = 0;
    private final int LOAD = 1;
    static int DEFAULT_WIDTH = 250;
    static int DEFAULT_HEIGHT = 250;
    static int DEFAULT_SMALL_WIDTH = 150;
    static int DEFAULT_SMALL_HEIGHT= 150;


    public NewsFeedRecycleViewAdapter(Context context, NewsFeed newsFeed, int count, String type) {
        this.context = context;
        this.newsFeed = newsFeed;
        this.count = count;
        this.type = type;
    }

    @Override
    public int getItemViewType(int position) {

        if(newsFeed.getRows().get(position).getType().equals(Constants.TYPE_NEWSFEED)){
            return NEWSFEED;
        }else{
            return LOAD;
        }
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public int getItemCount() {
        return this.count;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position, List<Object> payloads) {
        super.onBindViewHolder(holder, position, payloads);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_load, parent, false);
        if(viewType == LOAD) {
            return new LoadMoreDataHolder(LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.list_item_load, parent, false));
        }else if(viewType == NEWSFEED) {
            itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.list_item, parent, false);
        }
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder myViewHolder, int position) {
        if(position>=getItemCount()-1 && isMoreDataAvailable && !isLoading && loadMoreDataListener!=null){
            isLoading = true;
            loadMoreDataListener.onLoadMore();
        }

        if(getItemViewType(position)==NEWSFEED){
            ((MyViewHolder)myViewHolder).bindData(newsFeed.getRows().get(position),this.context,position);
        }
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.heading) public TextView heading;
        @BindView(R.id.description) public TextView description;
        @BindView(R.id.imagecontent) public ImageView imagedata;
        @BindView(R.id.textscrollview) public ScrollView textviewScroller;

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            description.setOnTouchListener(new View.OnTouchListener() {

                public boolean onTouch(View v, MotionEvent event) {
                    // Disallow the touch request for parent scroll on touch of child view
                    v.getParent().requestDisallowInterceptTouchEvent(true);
                    return false;
                }
            });
            description.setMovementMethod(new ScrollingMovementMethod());
        }
        void bindData(Row row, final Context context, int position){
            heading.setText(row.getTitle());
            description.setText(row.getDescription());
            int width =  DEFAULT_WIDTH;
            int height = DEFAULT_HEIGHT;
            if(row.getDescription() != null && row.getDescription().length() < 50 || row.getDescription() == null) {
                RequestOptions requestOptions = new RequestOptions()
                        .override(DEFAULT_SMALL_WIDTH,DEFAULT_SMALL_HEIGHT)
                        .centerCrop();
                Glide.with(context)
                        .load(R.drawable.no_image)
                        .apply(requestOptions)
                        .into(this.imagedata);
            } else {
                RequestOptions requestOptions = new RequestOptions()
                        .centerCrop()
                        .error(R.drawable.no_image)
                        .override(width, height);
                Glide.with(context)
                        .load(row.getImageHref())
                        .apply(requestOptions)
                        .into(this.imagedata);
            }
        }
    }

    interface OnLoadMoreDataListener{
        void onLoadMore();
    }

    public void setLoadMoreListener(OnLoadMoreDataListener loadDataMoreListener) {
        this.loadMoreDataListener = loadDataMoreListener;
    }

    static class LoadMoreDataHolder extends RecyclerView.ViewHolder{
        public LoadMoreDataHolder(View itemView) {
            super(itemView);
        }
    }

    public void setMoreDataAvailable(boolean moreDataAvailable) {
        isMoreDataAvailable = moreDataAvailable;
    }

    public void notifyDataChanged(){
        notifyDataSetChanged();
        isLoading = false;
    }
}
