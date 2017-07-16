package com.matthewregis.barcodescanner.ui.item_info;

import com.matthewregis.barcodescanner.ui.base.MvpView;

public interface ItemInfoMvpView extends MvpView {

    void shareItemInfo(String info);

    void setItemInfo(String info);

    String getItemInfo();

    void setImage(String url);

    //show


}
