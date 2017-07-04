package com.matthewregis.barcodescanner.data;
import com.matthewregis.barcodescanner.data.local.PrefHelper;
import com.matthewregis.barcodescanner.data.remote.BarcodeService;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class DataManager {

    private final BarcodeService mBarcodeService;
    private final PrefHelper mPrefHelper;

    @Inject
    public DataManager(BarcodeService ribotsService,
                       PrefHelper prefHelper) {
        mBarcodeService = ribotsService;
        mPrefHelper = prefHelper;

    }

    public PrefHelper getPrefHelper() {
        return mPrefHelper;
    }

    public BarcodeService getBarcodeService() {
        return mBarcodeService;
    }
}
