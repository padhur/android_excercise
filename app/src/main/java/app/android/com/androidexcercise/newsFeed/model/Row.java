package app.android.com.androidexcercise.newsFeed.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Row implements Parcelable
{
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("imageHref")
    @Expose
    private String imageHref;
    private String type;
    public String getTitle ()
    {
        return title;
    }

    public void setTitle (String title)
    {
        this.title = title;
    }

    public String getDescription ()
    {
        return description;
    }

    public void setDescription (String description)
    {
        this.description = description;
    }

    public String getImageHref ()
    {
        return imageHref;
    }

    public void setImageHref (String imageHref)
    {
        this.imageHref = imageHref;
    }

    public String getType ()
    {
        return type;
    }

    public void setType (String type)
    {
        this.type = type;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.title);
        parcel.writeString(this.description);
        parcel.writeString(this.imageHref);
        parcel.writeString(this.type);
    }

    protected Row(Parcel parcel) {
        this.title = parcel.readString();
        this.description = parcel.readString();
        this.imageHref = parcel.readString();
        this.type = parcel.readString();
    }

    public static final Creator<Row> CREATOR = new Creator<Row>() {
        @Override
        public Row createFromParcel(Parcel source) {
            return new Row(source);
        }

        @Override
        public Row[] newArray(int size) {
            return new Row[size];
        }
    };
}
