package app.android.com.androidexcercise.newsFeed.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;


public class NewsFeed implements Parcelable
{
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("rows")
    @Expose
    private ArrayList<Row> rows = null;

    public String getTitle ()
    {
        return title;
    }

    public void setTitle (String title)
    {
        this.title = title;
    }

    public ArrayList<Row> getRows ()
    {
        return rows;
    }

    public void setRows (ArrayList<Row> rows)
    {
        this.rows = rows;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.title);
        parcel.writeTypedList(this.rows);
    }

    public NewsFeed() {

    }

    protected NewsFeed(Parcel parcel) {
        this.title = parcel.readString();
        this.rows = parcel.createTypedArrayList(Row.CREATOR);
    }

    public static final Parcelable.Creator<NewsFeed> CREATOR = new Parcelable.Creator<NewsFeed>() {
        @Override
        public NewsFeed createFromParcel(Parcel source) {
            return new NewsFeed(source);
        }

        @Override
        public NewsFeed[] newArray(int size) {
            return new NewsFeed[size];
        }
    };
}
