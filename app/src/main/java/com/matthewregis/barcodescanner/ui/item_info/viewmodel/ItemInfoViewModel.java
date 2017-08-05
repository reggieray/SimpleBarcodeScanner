package com.matthewregis.barcodescanner.ui.item_info.viewmodel;

import android.content.Context;
import android.content.Intent;
import android.databinding.ObservableField;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.matthewregis.barcodescanner.R;
import com.matthewregis.barcodescanner.data.DataManager;
import com.matthewregis.barcodescanner.data.local.persistence.Item;
import com.matthewregis.barcodescanner.injection.ApplicationContext;
import com.matthewregis.barcodescanner.injection.ConfigPersistent;
import com.matthewregis.barcodescanner.ui.base.viewmodel.BaseViewModel;
import com.matthewregis.barcodescanner.util.ItemInfoHelper;

import javax.inject.Inject;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;
import timber.log.Timber;

/**
 * Created by reg on 04/08/2017.
 */
@ConfigPersistent
public class ItemInfoViewModel implements BaseViewModel {

    private final Context mContext;
    private final DataManager mDataManager;
    private final CompositeSubscription mSubscriptions = new CompositeSubscription();

    public final ObservableField<String> itemInfoText = new ObservableField<>();
    public final ObservableField<Drawable> itemImage = new ObservableField<>();

    @Inject
    public ItemInfoViewModel(@ApplicationContext Context context, DataManager dataManager) {
        mContext = context;
        mDataManager = dataManager;
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onResume() {
        mSubscriptions.add(mDataManager.getItemDataSource()
                .getItemById(mDataManager.getPrefHelper().getCurrentItemId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Item>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Timber.e(e, "GetItemInfo");
                    }

                    @Override
                    public void onNext(Item item) {
                        itemInfoText.set(ItemInfoHelper.BuildItemInfo(item));
                        if (!item.ImageUrl.isEmpty()) {
                            Glide.with(mContext)
                                    .load(item.ImageUrl)
                                    .asBitmap()
                                    .into(new SimpleTarget<Bitmap>() {
                                        @Override
                                        public void onResourceReady(Bitmap bitmap, GlideAnimation<? super Bitmap> glideAnimation) {
                                            Drawable drawable = new BitmapDrawable(mContext.getResources(), bitmap);
                                            itemImage.set(drawable);
                                        }
                                    });

                        }
                    }
                }));
    }

    @Override
    public void onDestroy() {
        mSubscriptions.unsubscribe();
    }

    public void shareClicked() {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, itemInfoText.get());
        sendIntent.setType("text/plain");
        mContext.startActivity(Intent.createChooser(sendIntent, mContext.getResources().getString(R.string.send_to)));
    }

}
