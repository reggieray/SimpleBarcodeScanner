package com.matthewregis.barcodescanner.data.viewmodel;

import android.os.Parcelable;
import android.support.annotation.Nullable;

import com.google.auto.value.AutoValue;
import com.google.gson.annotations.SerializedName;

/**
 * Created by matthew on 08/07/2017.
 */
@AutoValue
public abstract class ItemViewModel implements Parcelable {

    @SerializedName("id")
    public abstract int id();

    @Nullable
    @SerializedName("title")
    public abstract String title();

    @Nullable
    @SerializedName("description")
    public abstract String description();

    @Nullable
    @SerializedName("asin")
    public abstract String asin();

    @Nullable
    @SerializedName("imageurl")
    public abstract String imageurl();

    @Nullable
    @SerializedName("brand")
    public abstract String brand();

    @Nullable
    @SerializedName("datetime")
    public abstract String datetime();

    @Nullable
    @SerializedName("upc")
    public abstract String upc();

    public static Builder builder() {
        return new AutoValue_ItemViewModel.Builder();
    }

    @AutoValue.Builder
    public abstract static class Builder {
        public abstract Builder id(int id);

        public abstract Builder title(String title);

        public abstract Builder description(String description);

        public abstract Builder asin(String asin);

        public abstract Builder imageurl(String imageurl);

        public abstract Builder brand(String brand);

        public abstract Builder datetime(String datetime);

        public abstract Builder upc(String upc);

        public abstract ItemViewModel build();
    }
}
