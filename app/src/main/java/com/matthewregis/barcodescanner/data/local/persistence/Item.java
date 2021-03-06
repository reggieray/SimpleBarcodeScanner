package com.matthewregis.barcodescanner.data.local.persistence;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by matthew on 07/07/2017.
 * Represents a table in SQLite
 */
@Entity(tableName = "items")
public class Item {

    // empty constructor
    public Item() {
    }

    public Item(String title, String brand, String asin, String imageUrl, String dateTimeMillis, String upc, String description) {
        Title = title;
        Brand = brand;
        Asin = asin;
        ImageUrl = imageUrl;
        DateTimeMillis = dateTimeMillis;
        Upc = upc;
        Description = description;
    }

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "item_id")
    public int ItemId;

    @ColumnInfo(name = "title")
    public String Title;

    @ColumnInfo(name = "brand")
    public String Brand;

    @ColumnInfo(name = "asin")
    public String Asin;

    @ColumnInfo(name = "image_url")
    public String ImageUrl;

    @ColumnInfo(name = "date_time_millis")
    public String DateTimeMillis;

    @ColumnInfo(name = "upc")
    public String Upc;

    @ColumnInfo(name = "description")
    public String Description;
}
