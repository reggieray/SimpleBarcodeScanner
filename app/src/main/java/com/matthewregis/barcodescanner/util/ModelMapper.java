package com.matthewregis.barcodescanner.util;

import com.matthewregis.barcodescanner.data.local.SQLite.persistence.Item;
import com.matthewregis.barcodescanner.data.viewmodel.ItemViewModel;

import java.text.DateFormat;
import java.util.Date;

/**
 * Created by matthew on 08/07/2017.
 */

public final class ModelMapper {

    public static ItemViewModel ToViewModel(Item item) {
        return ItemViewModel.builder()
                .id(item.ItemId)
                .title(item.Title)
                .brand(item.Brand)
                .asin(item.Asin)
                .imageurl(item.ImageUrl)
                .datetime(String.valueOf(DateFormat.getDateTimeInstance().format(new Date(Long.parseLong(item.DateTimeMillis)))))
                .build();
    }

}
