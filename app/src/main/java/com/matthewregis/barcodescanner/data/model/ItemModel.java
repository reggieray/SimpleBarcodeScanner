package com.matthewregis.barcodescanner.data.model;

import android.os.Parcelable;
import android.support.annotation.*;

import com.google.auto.value.*;
import com.google.gson.*;
import com.google.gson.annotations.*;

import java.util.List;

/**
 * Created by matthew on 03/07/2017.
 * Represents the model for a item for the www.upcitemdb.com api
 */

@AutoValue
public abstract class ItemModel implements Parcelable{
    public static TypeAdapter<ItemModel> typeAdapter(Gson gson) {
        return new AutoValue_ItemModel.GsonTypeAdapter(gson);
    }

    public ItemModel() {
    }


    @Nullable
    @SerializedName("ean")
    public abstract String ean();

    @Nullable
    @SerializedName("title")
    public abstract String title();

    @Nullable
    @SerializedName("upc")
    public abstract String upc();

    @Nullable
    @SerializedName("gtin")
    public abstract String gtin();

    @Nullable
    @SerializedName("asin")
    public abstract String asin();

    @Nullable
    @SerializedName("description")
    public abstract String description();

    @Nullable
    @SerializedName("brand")
    public abstract String brand();

    @Nullable
    @SerializedName("model")
    public abstract String model();

    @Nullable
    @SerializedName("dimension")
    public abstract String dimension();

    @Nullable
    @SerializedName("weight")
    public abstract String weight();

    @Nullable
    @SerializedName("currency")
    public abstract String currency();

    @Nullable
    @SerializedName("images")
    public abstract List<String> images();

    public static Builder builder() {
        return new AutoValue_ItemModel.Builder();
    }

    @AutoValue.Builder
    public abstract static class Builder {

        public abstract Builder ean(String ean);

        public abstract Builder title(String title);

        public abstract Builder upc(String upc);

        public abstract Builder gtin(String gtin);

        public abstract Builder asin(String asin);

        public abstract Builder description(String description);

        public abstract Builder brand(String brand);

        public abstract Builder model(String model);

        public abstract Builder dimension(String dimension);

        public abstract Builder weight(String weight);

        public abstract Builder currency(String currency);

        public abstract Builder images(List<String> images);

        public abstract ItemModel build();
    }
}
