package com.matthewregis.barcodescanner.data.local.SQLite.persistence;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

/**
 * Created by matthew on 07/07/2017.
 */
@Dao
public interface ItemDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertItem(Item item);

    @Query("SELECT * FROM Items WHERE item_id = :Id LIMIT 1")
    Item getItemById(int Id);

    @Query("SELECT * FROM Items ORDER BY item_id DESC")
    List<Item> getItems();

    @Query("DELETE FROM Items")
    void deleteAllItems();

    @Query("DELETE FROM Items WHERE item_id = :Id")
    void deleteItemById(int Id);

}
