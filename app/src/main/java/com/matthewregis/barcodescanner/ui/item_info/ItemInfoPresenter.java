package com.matthewregis.barcodescanner.ui.item_info;
import com.matthewregis.barcodescanner.data.DataManager;
import com.matthewregis.barcodescanner.data.local.persistence.Item;
import com.matthewregis.barcodescanner.injection.ConfigPersistent;
import com.matthewregis.barcodescanner.ui.base.BasePresenter;
import com.matthewregis.barcodescanner.util.ItemInfoHelper;

import javax.inject.Inject;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;
import timber.log.Timber;

/**
 * Created by matthew on 10/07/2017.
 * Presenter for item info ui screen
 */
@ConfigPersistent
public class ItemInfoPresenter extends BasePresenter<ItemInfoMvpView> {

    private final DataManager mDataManager;
    private final CompositeSubscription mSubscriptions = new CompositeSubscription();

    @Inject
    public ItemInfoPresenter(DataManager dataManager) {
        mDataManager = dataManager;
    }

    @Override
    public void attachView(ItemInfoMvpView mvpView) {
        super.attachView(mvpView);
    }

    @Override
    public void detachView() {
        super.detachView();
        mSubscriptions.unsubscribe();
    }

    public void OnShareRequest() {
        getMvpView().shareItemInfo(getMvpView().getItemInfo());
    }

    public void OnGetItemInfo() {
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
                        getMvpView().setItemInfo(ItemInfoHelper.BuildItemInfo(item));
                        if (!item.ImageUrl.isEmpty()) {
                            getMvpView().setImage(item.ImageUrl);
                        }
                    }
                }));
    }

}
