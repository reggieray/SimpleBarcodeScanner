package com.matthewregis.barcodescanner.data.model;

import android.support.annotation.*;

import com.google.auto.value.*;
import com.google.gson.*;
import com.google.gson.annotations.*;

import java.util.List;

/**
 * Created by matthew on 03/07/2017.
 */

@AutoValue
public abstract class ItemModel {
    public static TypeAdapter<ItemModel> typeAdapter(Gson gson) {
        return new AutoValue_ItemModel.GsonTypeAdapter(gson);
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
}
