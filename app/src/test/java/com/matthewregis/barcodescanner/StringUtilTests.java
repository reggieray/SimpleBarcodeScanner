package com.matthewregis.barcodescanner;

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
public class StringUtilTests {

    private StringUtil mStringUtil;

    @Before
    public void setUp() {
        mStringUtil = new StringUtil();
    }

    @Test
    public void ShouldValidateBarcode() throws Exception {
        assertEquals(mStringUtil.IsValidBarCode("345345"), false);
        assertEquals(mStringUtil.IsValidBarCode("505058223826"), true);
        assertEquals(mStringUtil.IsValidBarCode("5050582238266"), true);
        assertEquals(mStringUtil.IsValidBarCode("ew53s546df45"), false);
    }

}
