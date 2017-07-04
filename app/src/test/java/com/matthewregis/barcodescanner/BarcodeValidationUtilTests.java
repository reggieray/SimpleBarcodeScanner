package com.matthewregis.barcodescanner;

import com.matthewregis.barcodescanner.util.BarcodeValidationUtil;
import com.matthewregis.barcodescanner.util.StringUtil;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;

/**
 * Created by matthew on 04/07/2017.
 */
@RunWith(MockitoJUnitRunner.class)
public class BarcodeValidationUtilTests {

    private BarcodeValidationUtil mBarcodeValidationUtil;

    @Before
    public void setUp() {
        mBarcodeValidationUtil = new BarcodeValidationUtil();
    }

    @Test
    public void ShouldValidateBarcode() throws Exception {
        assertEquals(mBarcodeValidationUtil.IsValidBarCode("345345"), false);
        assertEquals(mBarcodeValidationUtil.IsValidBarCode("505058223826"), true);
        assertEquals(mBarcodeValidationUtil.IsValidBarCode("5050582238266"), true);
        assertEquals(mBarcodeValidationUtil.IsValidBarCode("ew53s546df45"), false);
    }

}
