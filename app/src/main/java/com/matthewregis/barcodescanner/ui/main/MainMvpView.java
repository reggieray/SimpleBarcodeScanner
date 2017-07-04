package com.matthewregis.barcodescanner.ui.main;
import com.matthewregis.barcodescanner.ui.base.MvpView;

public interface MainMvpView extends MvpView {

    void setResultText(String result);

    void setProductImage(String url);

    void showLoadingView();

    void showView();

    void showImage();

    void hideImage();

    void showBarcodeInput();

    void showError(String error);

    void showHelperText();

}
