package com.matthewregis.barcodescanner.ui.main;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.Toolbar;
import android.text.method.CharacterPickerDialog;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.bumptech.glide.Glide;
import com.google.android.gms.vision.barcode.Barcode;
import com.matthewregis.barcodescanner.R;
import com.matthewregis.barcodescanner.ui.base.BaseActivity;
import com.matthewregis.barcodescanner.util.DialogFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import devliving.online.mvbarcodereader.MVBarcodeScanner;
import timber.log.Timber;

public class MainActivity extends BaseActivity implements MainMvpView, View.OnClickListener {

    @Inject
    MainPresenter mPresenter;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    @BindView(R.id.fab2)
    FloatingActionButton fab2;
    @BindView(R.id.result)
    AppCompatTextView result;
    @BindView(R.id.image)
    ImageView image;
    @BindView(R.id.progess_bar)
    ProgressBar progessBar;

    @MVBarcodeScanner.BarCodeFormat
    int[] mFormats = new int[]{Barcode.ALL_FORMATS};
    final int REQ_CODE = 12;
    Barcode mBarcode;

    final static HashMap<Integer, String> TYPE_MAP;
    final static String[] barcodeTypeItems;

    static {
        TYPE_MAP = new HashMap<>();

        TYPE_MAP.put(Barcode.ALL_FORMATS, "All Formats");
        TYPE_MAP.put(Barcode.AZTEC, "Aztec");
        TYPE_MAP.put(Barcode.CALENDAR_EVENT, "Calendar Event");
        TYPE_MAP.put(Barcode.CODABAR, "Codabar");
        TYPE_MAP.put(Barcode.CODE_39, "Code 39");
        TYPE_MAP.put(Barcode.CODE_93, "Code 93");
        TYPE_MAP.put(Barcode.CODE_128, "Code 128");
        TYPE_MAP.put(Barcode.CONTACT_INFO, "Contact Info");
        TYPE_MAP.put(Barcode.DATA_MATRIX, "Data Matrix");
        TYPE_MAP.put(Barcode.DRIVER_LICENSE, "Drivers License");
        TYPE_MAP.put(Barcode.EAN_8, "EAN 8");
        TYPE_MAP.put(Barcode.EAN_13, "EAN 13");
        TYPE_MAP.put(Barcode.EMAIL, "Email");
        TYPE_MAP.put(Barcode.GEO, "Geo");
        TYPE_MAP.put(Barcode.ISBN, "ISBN");
        TYPE_MAP.put(Barcode.ITF, "ITF");
        TYPE_MAP.put(Barcode.PDF417, "PDF 417");
        TYPE_MAP.put(Barcode.PHONE, "Phone");
        TYPE_MAP.put(Barcode.QR_CODE, "QR Code");
        TYPE_MAP.put(Barcode.PRODUCT, "Product");
        TYPE_MAP.put(Barcode.SMS, "SMS");
        TYPE_MAP.put(Barcode.UPC_A, "UPC A");
        TYPE_MAP.put(Barcode.UPC_E, "UPC E");
        TYPE_MAP.put(Barcode.TEXT, "Text");
        TYPE_MAP.put(Barcode.URL, "URL");

        List<String> items = new ArrayList<>(TYPE_MAP.values());
        Collections.sort(items);
        String[] tempArray = new String[items.size()];
        tempArray = items.toArray(tempArray);
        barcodeTypeItems = tempArray;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityComponent().inject(this);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mPresenter.attachView(this);
        setSupportActionBar(toolbar);
        fab.setOnClickListener(this);
        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.OnRequestInput();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
    }

    @Override
    public void onClick(View v) {
        new MVBarcodeScanner.Builder()
                .setScanningMode(MVBarcodeScanner.ScanningMode.SINGLE_AUTO)
                .setFormats(mFormats)
                .build()
                .launchScanner(this, REQ_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQ_CODE) {
            if (resultCode == RESULT_OK && data != null && data.getExtras() != null) {
                Timber.d(String.format("$s$s", "BARCODE-SCANNER", "onActivityResult inside block called"));
                if (data.getExtras().containsKey(MVBarcodeScanner.BarcodeObject)) {
                    mBarcode = data.getParcelableExtra(MVBarcodeScanner.BarcodeObject);
                }
                updateBarcodeInfo();
            }
        }
    }

    String getBarcodeFormatName(int format) {
        return TYPE_MAP.get(format);
    }

    void updateBarcodeInfo() {
        StringBuilder builder = new StringBuilder();

        if (mBarcode != null) {
            Timber.d(String.format("$s$s", "BARCODE-SCANNER", "got barcode"));
            builder.append("Type: " + getBarcodeFormatName(mBarcode.format) + "\nData: " + mBarcode.rawValue + "\n\n");
            mPresenter.GetBarcodeInfo(mBarcode.rawValue);
        }
    }

    @Override
    public void setResultText(String resultText) {
        result.setText(resultText);
    }

    @Override
    public void setProductImage(String url) {
        Glide.with(this).load(url).into(image);
    }

    @Override
    public void showLoadingView() {
        result.setVisibility(View.GONE);
        image.setVisibility(View.GONE);
        progessBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void showView() {
        progessBar.setVisibility(View.GONE);
        result.setVisibility(View.VISIBLE);
    }

    @Override
    public void showImage() {
        image.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideImage() {
        image.setVisibility(View.GONE);
    }

    @Override
    public void showBarcodeInput() {
        DialogFactory.createCustomMaterialDialogInput(this, "Barcode input", "Please input a EAN or UPC barcode", "eg. 6867441000198", "", "Ok", new MaterialDialog.InputCallback() {
            @Override
            public void onInput(@NonNull MaterialDialog dialog, CharSequence input) {

            }
        }, new MaterialDialog.SingleButtonCallback() {
            @Override
            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                mPresenter.OnInputOfBarcode(dialog.getInputEditText().getText().toString());
            }
        }).show();
    }

    @Override
    public void showError(String error) {
        DialogFactory.createOkMaterialDialog(this, error, new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {

            }
        }).show();
    }
}
