package com.matthewregis.barcodescanner.data.local.SQLite.persistence;

import com.matthewregis.barcodescanner.data.local.SQLite.ItemDataSource;

import java.util.List;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by matthew on 07/07/2017.
 */

public class LocalItemDataSource implements ItemDataSource {

    private final ItemDao mItemDao;

    public LocalItemDataSource(ItemDao itemDao) {
        this.mItemDao = itemDao;
    }

    @Override
    public Observable<Item> getItemById(final Integer Id) {
        return Observable.create(new Observable.OnSubscribe<Item>() {

            @Override
            public void call(Subscriber<? super Item> subscriber) {
                if (subscriber.isUnsubscribed()) return;
                subscriber.onNext(mItemDao.getItemById(Id));
                subscriber.onCompleted();
            }
        });
    }

    @Override
    public Observable<List<Item>> getItems() {
        return Observable.create(new Observable.OnSubscribe<List<Item>>() {
            @Override
            public void call(Subscriber<? super List<Item>> subscriber) {
                if (subscriber.isUnsubscribed()) return;
                subscriber.onNext(mItemDao.getItems());
                subscriber.onCompleted();
            }
        });
    }

    @Override
    public Observable<Item> insertOrUpdateItem(final Item item) {
        return Observable.create(new Observable.OnSubscribe<Item>() {
            @Override
            public void call(Subscriber<? super Item> subscriber) {
                if (subscriber.isUnsubscribed()) return;
                mItemDao.insertItem(item);
                subscriber.onNext(item);
                subscriber.onCompleted();
            }
        });
    }

    @Override
    public Observable<Boolean> deleteAllItems() {
        return Observable.create(new Observable.OnSubscribe<Boolean>() {
            @Override
            public void call(Subscriber<? super Boolean> subscriber) {
                if (subscriber.isUnsubscribed()) return;
                mItemDao.deleteAllItems();
                subscriber.onNext(true);
                subscriber.onCompleted();
            }
        });
    }

    @Override
    public Observable<Integer> deleteItemById(final Integer Id) {
        return Observable.create(new Observable.OnSubscribe<Integer>() {
            @Override
            public void call(Subscriber<? super Integer> subscriber) {
                if (subscriber.isUnsubscribed()) return;
                mItemDao.deleteItemById(Id);
                subscriber.onNext(Id);
                subscriber.onCompleted();
            }
        });
    }
}
