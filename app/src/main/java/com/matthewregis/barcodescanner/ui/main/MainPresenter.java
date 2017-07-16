package com.matthewregis.barcodescanner.ui.main;

import javax.inject.Inject;

import com.matthewregis.barcodescanner.injection.ApplicationContext;
import com.matthewregis.barcodescanner.injection.ConfigPersistent;

import android.content.Context;

import com.matthewregis.barcodescanner.R;
import com.matthewregis.barcodescanner.data.DataManager;
import com.matthewregis.barcodescanner.data.local.persistence.Item;
import com.matthewregis.barcodescanner.data.model.BarcodeModel;
import com.matthewregis.barcodescanner.data.viewmodel.ItemViewModel;
import com.matthewregis.barcodescanner.ui.base.BasePresenter;
import com.matthewregis.barcodescanner.util.BarcodeValidationUtil;
import com.matthewregis.barcodescanner.util.ModelMapper;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;
import timber.log.Timber;


@ConfigPersistent
public class MainPresenter extends BasePresenter<MainMvpView> {

    private final Context mContext;
    private final DataManager mDataManager;
    private final CompositeSubscription mSubscriptions = new CompositeSubscription();

    @Inject
    public MainPresenter(@ApplicationContext Context context, DataManager dataManager) {
        mContext = context;
        mDataManager = dataManager;
    }

    @Override
    public void attachView(MainMvpView mvpView) {
        super.attachView(mvpView);
    }

    @Override
    public void detachView() {
        super.detachView();
        mSubscriptions.unsubscribe();
    }

    public void GetBarcodeInfo(String barcode) {
        if (!BarcodeValidationUtil.IsValidBarCode(barcode)) {
            getMvpView().showError(mContext.getString(R.string.error_barcode_invalid));
            return;
        }

        getMvpView().showProgressbar();
        mSubscriptions.add(mDataManager.getBarcodeService()
                .getProductInfo(barcode)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<BarcodeModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        getMvpView().showError(mContext.getString(R.string.error_api_failed));
                        Timber.e(e, this.toString());
                        getMvpView().hideProgressbar();
                    }

                    @Override
                    public void onNext(BarcodeModel barcodeModel) {
                        OnBarcodeResult(barcodeModel);
                        getMvpView().hideProgressbar();
                    }
                }));
    }

    public void OnBarcodeResult(BarcodeModel barcodeModel) {

        if (barcodeModel.items().isEmpty()) {
            getMvpView().showError(mContext.getString(R.string.error_empty_product_info));
            return;
        }
        String imgUrl = barcodeModel.items().get(0).images().isEmpty() ? "" : barcodeModel.items().get(0).images().get(0);
        long timeMillis = System.currentTimeMillis();
        Timber.i("currentTimeMillis: %s", String.valueOf(timeMillis));
        Item item = new Item(barcodeModel.items().get(0).title(), barcodeModel.items().get(0).brand(), barcodeModel.items().get(0).asin(), imgUrl, String.valueOf(timeMillis), barcodeModel.items().get(0).upc(), barcodeModel.items().get(0).description());
        mSubscriptions.add(mDataManager.getItemDataSource()
                .insertOrUpdateItem(item)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Item>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onNext(Item item) {
                        getMvpView().addItem(ModelMapper.ToViewModel(item));
                    }
                }));
    }

    public void OnRequestInput() {
        getMvpView().showBarcodeInput();
    }

    public void OnRequestBarcodeScanner() {
        getMvpView().launchBarcodeScanner();
    }

    public void OnInputOfBarcode(String barcode) {
        GetBarcodeInfo(barcode);
    }

    public void CheckToShowHelperText() {
        if (!mDataManager.getPrefHelper().hasSeenHelperText()) {
            getMvpView().showHelperText();
        }
    }

    public void OnHelperTextSeen() {
        mDataManager.getPrefHelper().setHasSeenHelperText(true);
    }

    public void LoadItems() {
        mSubscriptions.add(mDataManager.getItemDataSource()
                .getItems()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<Item>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Timber.d(e, "ERROR: %s", e.getMessage());
                    }

                    @Override
                    public void onNext(List<Item> items) {
                        List<ItemViewModel> list = new ArrayList<>();

                        for (Item item : items) {
                            list.add(ModelMapper.ToViewModel(item));
                        }

                        getMvpView().setItemList(list);
                        getMvpView().setSwipeRefreshing(false);
                    }
                }));
    }

    public void RemoveItem(final ItemViewModel item) {
        mSubscriptions.add(mDataManager.getItemDataSource()
                .deleteItemById(item.id())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Integer>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Timber.d(e, "ERROR: %s", e.getMessage());
                    }

                    @Override
                    public void onNext(Integer Id) {
                        OnItemRemoved(item);
                    }
                }));
    }

    public void OnItemRemoved(ItemViewModel item) {
        getMvpView().removeItem(item);
    }

    public void OnItemListEmpty() {
        getMvpView().showEmptyListText();
    }

    public void OnItemListPopulated() {
        getMvpView().hideEmptyListText();
    }

    public void OnItemClick(ItemViewModel viewModel) {
        mDataManager.getPrefHelper().setCurrentItemId(viewModel.id());
        getMvpView().navigateToItemInfo();
    }

    public void OnMenuDeleteAllSelected() {
        getMvpView().showConfirmDeleteAllDialog();
    }

    public void OnDeleteAllConfirmed() {
        mSubscriptions.add(mDataManager.getItemDataSource()
                .deleteAllItems()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Boolean>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        Timber.d(e, "ERROR: %s", e.getMessage());
                    }

                    @Override
                    public void onNext(Boolean value) {
                        List<ItemViewModel> list = new ArrayList<>();
                        getMvpView().setItemList(list);
                    }
                }));
    }

}
