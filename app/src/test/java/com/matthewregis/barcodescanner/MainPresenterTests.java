package com.matthewregis.barcodescanner;

import android.content.Context;

import com.matthewregis.barcodescanner.data.DataManager;
import com.matthewregis.barcodescanner.data.local.PrefHelper;
import com.matthewregis.barcodescanner.data.model.BarcodeModel;
import com.matthewregis.barcodescanner.data.model.ItemModel;
import com.matthewregis.barcodescanner.ui.main.MainMvpView;
import com.matthewregis.barcodescanner.ui.main.MainPresenter;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by matthew on 04/07/2017.
 */
@RunWith(MockitoJUnitRunner.class)
public class MainPresenterTests {

    @Mock
    MainMvpView mMainMvpView;
    @Mock
    DataManager mMockDataManager;
    @Mock
    PrefHelper mPrefHelper;
    @Mock
    BarcodeScannerApplication mApplication;
    @Mock
    Context mContext;
    private MainPresenter mPresenter;


    @Before
    public void setUp() {
        mPresenter = new MainPresenter(mContext, mMockDataManager);
        mPresenter.attachView(mMainMvpView);
        when(mMockDataManager.getPrefHelper()).thenReturn(mPrefHelper);
    }

    @After
    public void tearDown() {
        mPresenter.detachView();
    }

    @Test
    public void ShouldShowInput() throws Exception {
        mPresenter.OnRequestInput();
        verify(mMainMvpView, times(1)).showBarcodeInput();
    }

    @Test
    public void ShouldShowErrorOnInvalidInput() throws Exception {
        String error = "Sorry that barcode doesn't look valid UPC, ISBN or EAN barcode.";
        when(mContext.getString(R.string.error_barcode_invalid)).thenReturn(error);
        mPresenter.OnInputOfBarcode("2324234");
        verify(mMainMvpView, times(1)).hideImage();
        verify(mMainMvpView, times(1)).setResultText("");
        verify(mMainMvpView, times(1)).showError(error);
    }

    @Test
    public void ShouldShowProductImage() throws Exception {
        String imgUrl = "https://madeupurl.com/img.jpg";
        mPresenter.LoadProductImage(imgUrl);
        verify(mMainMvpView, times(1)).setProductImage(imgUrl);
        verify(mMainMvpView, times(1)).showImage();
    }

    @Test
    public void ShouldShowHelperTextOnFirstVisit() throws Exception {
        when(mPrefHelper.hasSeenHelperText()).thenReturn(false);
        mPresenter.CheckToShowHelperText();
        verify(mMainMvpView, times(1)).showHelperText();
    }

    @Test
    public void ShouldNotShowHelperTextAfterSeen() throws Exception {
        when(mPrefHelper.hasSeenHelperText()).thenReturn(true);
        mPresenter.CheckToShowHelperText();
        verify(mMainMvpView, never()).showHelperText();
    }

    @Test
    public void ShouldSetShownHelperTextAfterSeen() throws Exception {
        mPresenter.OnHelperTextSeen();
        verify(mPrefHelper, times(1)).setHasSeenHelperText(true);
    }

    @Test
    public void ShouldShowEmptyErrorMessageOnEmptyModel() throws Exception {
        String error = "Sorry that barcode doesn't look valid UPC, ISBN or EAN barcode.";
        when(mContext.getString(R.string.error_empty_product_info)).thenReturn(error);
        List<ItemModel> itemModelList = new ArrayList<>();
        BarcodeModel barcodeModel = BarcodeModel.builder().code("").total(100).items(itemModelList).build();
        mPresenter.OnBarcodeResult(barcodeModel);
        verify(mMainMvpView, times(1)).setResultText(error);
    }

    @Test
    public void ShouldShowProductInfo() throws Exception {
        List<String> images = new ArrayList<>();
        images.add("ImageUrl");
        ItemModel itemModel = ItemModel.builder().title("title").brand("Google").asin("12345678").images(images).build();
        List<ItemModel> itemModelList = new ArrayList<>();
        itemModelList.add(itemModel);
        BarcodeModel barcodeModel = BarcodeModel.builder().code("").total(100).items(itemModelList).build();
        String info = String.format("Title: %s\nBrand: %s\nAsin: %s", barcodeModel.items().get(0).title(), barcodeModel.items().get(0).brand(), barcodeModel.items().get(0).asin());

        mPresenter.OnBarcodeResult(barcodeModel);
        verify(mMainMvpView, times(1)).setResultText(info);
        verify(mMainMvpView, times(1)).setProductImage("ImageUrl");
        verify(mMainMvpView, times(1)).showImage();
    }

    @Test
    public void ShouldShowProductInfoNoImage() throws Exception {
        List<String> images = new ArrayList<>();
        ItemModel itemModel = ItemModel.builder().title("title").brand("Google").asin("12345678").images(images).build();
        List<ItemModel> itemModelList = new ArrayList<>();
        itemModelList.add(itemModel);
        BarcodeModel barcodeModel = BarcodeModel.builder().code("").total(100).items(itemModelList).build();
        String info = String.format("Title: %s\nBrand: %s\nAsin: %s", barcodeModel.items().get(0).title(), barcodeModel.items().get(0).brand(), barcodeModel.items().get(0).asin());

        mPresenter.OnBarcodeResult(barcodeModel);
        verify(mMainMvpView, times(1)).setResultText(info);
        verify(mMainMvpView, never()).setProductImage("ImageUrl");
        verify(mMainMvpView, never()).showImage();
    }







}
