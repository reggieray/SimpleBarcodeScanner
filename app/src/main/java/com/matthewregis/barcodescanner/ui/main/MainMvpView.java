package com.matthewregis.barcodescanner.ui.main;

import com.matthewregis.barcodescanner.data.viewmodel.ItemViewModel;
import com.matthewregis.barcodescanner.ui.base.MvpView;

import java.util.List;

public interface MainMvpView extends MvpView {

    void showBarcodeInput();

    void launchBarcodeScanner();

    void showError(String error);

    void showHelperText();

    void setSwipeRefreshing(boolean value);

    void setItemList(List<ItemViewModel> items);

    void addItemList(List<ItemViewModel> items);

    void addItem(ItemViewModel item);

    void showProgressbar();

    void hideProgressbar();

    void removeItem(ItemViewModel item);

    void showEmptyListText();

    void hideEmptyListText();

    void showConfirmDeleteAllDialog();
}
