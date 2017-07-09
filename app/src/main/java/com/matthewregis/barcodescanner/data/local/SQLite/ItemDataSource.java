package com.matthewregis.barcodescanner.data.local.SQLite;

import com.matthewregis.barcodescanner.data.local.SQLite.persistence.Item;

import java.util.List;

import rx.Observable;


/**
 * Created by matthew on 07/07/2017.
 */

public interface ItemDataSource {

    Observable<Item> getItem();

    Observable<List<Item>> getItems();

    Observable<Item> insertOrUpdateItem(Item item);

    Observable<Boolean> deleteAllItems();

    Observable<Integer> deleteItemById(Integer Id);

}
