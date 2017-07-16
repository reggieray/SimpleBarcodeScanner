package com.matthewregis.barcodescanner;

import android.content.Context;

import com.matthewregis.barcodescanner.data.DataManager;
import com.matthewregis.barcodescanner.data.local.ItemDataSource;
import com.matthewregis.barcodescanner.data.local.PrefHelper;
import com.matthewregis.barcodescanner.data.local.persistence.Item;
import com.matthewregis.barcodescanner.data.viewmodel.ItemViewModel;
import com.matthewregis.barcodescanner.ui.item_info.ItemInfoMvpView;
import com.matthewregis.barcodescanner.ui.item_info.ItemInfoPresenter;
import com.matthewregis.barcodescanner.ui.main.MainMvpView;
import com.matthewregis.barcodescanner.ui.main.MainPresenter;
import com.matthewregis.barcodescanner.util.ItemInfoHelper;
import com.matthewregis.barcodescanner.util.RxSchedulersOverrideRule;

import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Collections;

import rx.Observable;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyListOf;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by matthew on 04/07/2017.
 */
@RunWith(MockitoJUnitRunner.class)
public class ItemInfoPresenterTests {

    @Mock
    ItemInfoMvpView mItemInfoMvpView;
    @Mock
    DataManager mMockDataManager;
    @Mock
    PrefHelper mPrefHelper;
    @Mock
    ItemDataSource mItemDataSource;

    private ItemInfoPresenter mPresenter;

    @Rule
    public final RxSchedulersOverrideRule mOverrideSchedulersRule = new RxSchedulersOverrideRule();

    @Before
    public void setUp() {
        mPresenter = new ItemInfoPresenter(mMockDataManager);
        mPresenter.attachView(mItemInfoMvpView);
        when(mMockDataManager.getPrefHelper()).thenReturn(mPrefHelper);
        when(mMockDataManager.getItemDataSource()).thenReturn(mItemDataSource);
    }

    @After
    public void tearDown() {
        mPresenter.detachView();
    }


    @Test
    public void ShouldShareInfo() throws Exception {
        String itemInfo = "ItemInfo";
        when(mItemInfoMvpView.getItemInfo()).thenReturn(itemInfo);
        mPresenter.OnShareRequest();
        verify(mItemInfoMvpView, times(1)).getItemInfo();
        verify(mItemInfoMvpView, times(1)).shareItemInfo(itemInfo);
    }

    @Test
    public void ShouldSetItemInfoOnGetItemInfoNoImage() throws Exception {
        int ItemId = 1;
        Item item = new Item();
        item.ItemId = ItemId;

        when(mPrefHelper.getCurrentItemId()).thenReturn(ItemId);
        when(mItemDataSource.getItemById(ItemId)).thenReturn(Observable.just(item));

        mPresenter.OnGetItemInfo();
        verify(mItemInfoMvpView, times(1)).setItemInfo(ItemInfoHelper.BuildItemInfo(item));
        verify(mItemInfoMvpView, never()).setImage(any(String.class));
    }

    @Test
    public void ShouldSetItemInfoOnGetItemInfoWithImage() throws Exception {
        String imgUrl = "https://imageurl.com";
        int ItemId = 1;
        Item item = new Item();
        item.ItemId = ItemId;
        item.ImageUrl = imgUrl;

        when(mPrefHelper.getCurrentItemId()).thenReturn(ItemId);
        when(mItemDataSource.getItemById(ItemId)).thenReturn(Observable.just(item));

        mPresenter.OnGetItemInfo();
        verify(mItemInfoMvpView, times(1)).setItemInfo(ItemInfoHelper.BuildItemInfo(item));
        verify(mItemInfoMvpView, times(1)).setImage(imgUrl);
    }


}
