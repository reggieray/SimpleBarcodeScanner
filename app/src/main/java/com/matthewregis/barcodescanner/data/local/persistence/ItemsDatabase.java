package com.matthewregis.barcodescanner.data.local.persistence;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

/**
 * Created by matthew on 07/07/2017.
 */
@Database(entities = {Item.class}, version = 1)
public abstract class ItemsDatabase extends RoomDatabase {

    private static ItemsDatabase INSTANCE;

    public abstract ItemDao itemDao();

    public static ItemsDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (ItemsDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            ItemsDatabase.class, "Items.db")
                            .build();
                }
            }
        }
        return INSTANCE;
    }

}