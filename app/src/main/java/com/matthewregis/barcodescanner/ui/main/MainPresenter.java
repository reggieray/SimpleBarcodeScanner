package com.matthewregis.barcodescanner.ui.main;

import android.content.Context;

import com.matthewregis.barcodescanner.data.DataManager;
import com.matthewregis.barcodescanner.data.model.BarcodeModel;
import com.matthewregis.barcodescanner.injection.ApplicationContext;
import com.matthewregis.barcodescanner.injection.ConfigPersistent;
import com.matthewregis.barcodescanner.ui.base.BasePresenter;
import com.matthewregis.barcodescanner.util.StringUtil;

import javax.inject.Inject;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;
import timber.log.Timber;

@ConfigPersistent
public class MainPresenter extends BasePresenter<MainMvpView> {

    private final Context mContext;
    private final DataManager mDataManager;
    private CompositeSubscription mSubscriptions;

    @Inject
    public MainPresenter(@ApplicationContext Context context, DataManager dataManager) {
        mContext = context;
        mDataManager = dataManager;
        mSubscriptions = new CompositeSubscription();
    }

    @Override
    public void attachView(MainMvpView mvpView) {
        super.attachView(mvpView);
    }

    @Override
    public void detachView() {
        super.detachView();
        if (mSubscriptions != null) mSubscriptions.unsubscribe();
    }

    public void GetBarcodeInfo(String barcode) {
        if (!StringUtil.IsValidBarCode(barcode)) {
            getMvpView().hideImage();
            getMvpView().setResultText("");
            getMvpView().showError("Sorry that barcode doesn't look valid EAN or UPC barcode.");
            return;
        }
        getMvpView().hideImage();
        getMvpView().showLoadingView();
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
                        getMvpView().setResultText("Error on fetching info");
                        Timber.e(e, this.toString());
                        getMvpView().showView();
                    }

                    @Override
                    public void onNext(BarcodeModel barcodeModel) {
                        OnBarcodeResult(barcodeModel);
                        getMvpView().showView();
                    }
                }));
    }

    public void OnBarcodeResult(BarcodeModel barcodeModel) {
        if (barcodeModel.items() != null) {
            if (barcodeModel.items().size() > 0) {
                String result = String.format("Title: %s\nBrand: %s\nAsin: %s", barcodeModel.items().get(0).title(), barcodeModel.items().get(0).brand(), barcodeModel.items().get(0).asin());
                if (barcodeModel.items().get(0).images().size() > 0) {
                    String imageUrl = barcodeModel.items().get(0).images().get(0);
                    Timber.d(String.format("Image Url %s", imageUrl));
                    LoadProductImage(imageUrl);
                }
                getMvpView().setResultText(result);
            } else {
                getMvpView().setResultText("No results");
            }
        }
    }

    public void LoadProductImage(String imageUrl) {
        getMvpView().setProductImage(imageUrl);
        getMvpView().showImage();
    }

    public void OnRequestInput() {
        getMvpView().showBarcodeInput();
    }

    public void OnInputOfBarcode(String barcode) {
        GetBarcodeInfo(barcode);
    }
}
