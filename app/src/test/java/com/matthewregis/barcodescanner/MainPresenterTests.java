package com.matthewregis.barcodescanner;

import android.content.Context;

import com.matthewregis.barcodescanner.data.DataManager;
import com.matthewregis.barcodescanner.data.local.PrefHelper;
import com.matthewregis.barcodescanner.data.local.ItemDataSource;
import com.matthewregis.barcodescanner.data.local.persistence.Item;
import com.matthewregis.barcodescanner.data.viewmodel.ItemViewModel;
import com.matthewregis.barcodescanner.ui.main.MainMvpView;
import com.matthewregis.barcodescanner.ui.main.MainPresenter;
import com.matthewregis.barcodescanner.util.RxSchedulersOverrideRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Collections;

import rx.Observable;

import static org.mockito.ArgumentMatchers.anyListOf;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by matthew on 04/07/2017.
 * Main Presenter tests
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
    ItemDataSource mItemDataSource;
    @Mock
    BarcodeScannerApplication mApplication;
    @Mock
    Context mContext;
    private MainPresenter mPresenter;

    @Rule
    public final RxSchedulersOverrideRule mOverrideSchedulersRule = new RxSchedulersOverrideRule();

    @Before
    public void setUp() {
        mPresenter = new MainPresenter(mContext, mMockDataManager);
        mPresenter.attachView(mMainMvpView);
        when(mMockDataManager.getPrefHelper()).thenReturn(mPrefHelper);
        when(mMockDataManager.getItemDataSource()).thenReturn(mItemDataSource);
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
        verify(mMainMvpView, times(1)).showError(error);
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
    public void ShouldRemoveItemOnItemRemoved() throws Exception {
        ItemViewModel itemViewModel = ItemViewModel.builder().id(1).build();
        mPresenter.OnItemRemoved(itemViewModel);
        verify(mMainMvpView, times(1)).removeItem(itemViewModel);
    }

    @Test
    public void ShouldShowEmptyListText() throws Exception {
        mPresenter.OnItemListEmpty();
        verify(mMainMvpView, times(1)).showEmptyListText();
    }

    @Test
    public void ShouldHideEmptyListText() throws Exception {
        mPresenter.OnItemListPopulated();
        verify(mMainMvpView, times(1)).hideEmptyListText();
    }

    @Test
    public void ShouldShowConfirmDeleteAllDialog() throws Exception {
        mPresenter.OnMenuDeleteAllSelected();
        verify(mMainMvpView, times(1)).showConfirmDeleteAllDialog();
    }

    @Test
    public void ShouldLoadItems() throws Exception {
        when(mItemDataSource.getItems())
                .thenReturn(Observable.just(Collections.<Item>emptyList()));
        mPresenter.LoadItems();
        verify(mMainMvpView, times(1)).setItemList(anyListOf(ItemViewModel.class));
        verify(mMainMvpView, times(1)).setSwipeRefreshing(false);
    }

    @Test
    public void ShouldRemoveItem() throws Exception {
        ItemViewModel itemViewModel = ItemViewModel.builder().id(1).build();
        when(mItemDataSource.deleteItemById(itemViewModel.id()))
                .thenReturn(Observable.just(itemViewModel.id()));
        mPresenter.RemoveItem(itemViewModel);
        verify(mMainMvpView, times(1)).removeItem(itemViewModel);
    }

    @Test
    public void ShouldRemoveAllItems() throws Exception {
        when(mItemDataSource.deleteAllItems())
                .thenReturn(Observable.just(true));
        mPresenter.OnDeleteAllConfirmed();
        verify(mMainMvpView, times(1)).setItemList(Collections.<ItemViewModel>emptyList());
    }


}
