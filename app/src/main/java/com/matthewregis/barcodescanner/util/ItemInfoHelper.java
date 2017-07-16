package com.matthewregis.barcodescanner.util;

import com.matthewregis.barcodescanner.data.local.persistence.Item;

/**
 * Created by matthew on 15/07/2017.
 * Helper to build item info string
 */

public final class ItemInfoHelper {

    public static String BuildItemInfo(Item item) {
        String info = "";
        if (item == null) return info;
        if (item.Upc != null) info = String.format("%s%s%s\n\n", info, "UPC: ", item.Upc);
        if (item.Title != null) info = String.format("%s%s%s\n\n", info, "Title: ", item.Title);
        if (item.Brand != null) info = String.format("%s%s%s\n\n", info, "Brand: ", item.Brand);
        if (item.Asin != null) info = String.format("%s%s%s\n\n", info, "Asin: ", item.Asin);
        if (item.Description != null)
            info = String.format("%s%s%s", info, "Description: ", item.Description);
        return info;
    }

}
