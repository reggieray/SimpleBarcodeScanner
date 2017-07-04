package com.matthewregis.barcodescanner.data.model;

import android.os.Parcelable;
import android.support.annotation.Nullable;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;

import java.util.List;

/**
 * Created by matthew on 03/07/2017.
 */
@AutoValue
public abstract class BarcodeModel implements Parcelable {
    public abstract String code();
    public abstract int total();
    public abstract List<ItemModel> items();

    public static TypeAdapter<BarcodeModel> typeAdapter(Gson gson) {
        return new AutoValue_BarcodeModel.GsonTypeAdapter(gson);
    }

    public static Builder builder() {
        return new AutoValue_BarcodeModel.Builder();
    }

    @AutoValue.Builder
    public abstract static class Builder {
        public abstract Builder code(String code);

        public abstract Builder total(int total);

        public abstract Builder items(List<ItemModel> items);

        public abstract BarcodeModel build();
    }
}
