package com.matthewregis.barcodescanner.ui.main;

import com.matthewregis.barcodescanner.data.viewmodel.ItemViewModel;

/**
 * Created by matthew on 07/07/2017.
 * Listener for item recyclerview list
 */

public interface MainAdapterListener {

    void onItemListEmpty();

    void onItemListPopulated();

    void onItemClick(ItemViewModel viewModel);
}
