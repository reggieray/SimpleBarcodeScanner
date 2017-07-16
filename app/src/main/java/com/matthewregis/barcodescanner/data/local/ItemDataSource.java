package com.matthewregis.barcodescanner.data.local;
import com.matthewregis.barcodescanner.data.local.persistence.Item;

import java.util.List;

import rx.Observable;


/**
 * Created by matthew on 07/07/2017.
 * Interface for item table
 */

public interface ItemDataSource {

    Observable<Item> getItemById(Integer Id);

    Observable<List<Item>> getItems();

    Observable<Item> insertOrUpdateItem(Item item);

    Observable<Boolean> deleteAllItems();

    Observable<Integer> deleteItemById(Integer Id);

}
