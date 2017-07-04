package com.matthewregis.barcodescanner.ui.main;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.Toolbar;
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
    int[] mFormats = new int[]{Barcode.UPC_E, Barcode.UPC_A, Barcode.ISBN, Barcode.EAN_8, Barcode.EAN_13}; // Only use codes that upcitemdb.com support eg. UPC, ISBN or EAN
    final int REQ_CODE = 12;
    Barcode mBarcode;

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
    protected void onResume() {
        super.onResume();
        mPresenter.CheckToShowHelperText();
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
                    if (mBarcode != null) {
                        Timber.d(String.format("$s$s%s", "BARCODE-SCANNER", "got barcode", mBarcode.rawValue));
                        mPresenter.GetBarcodeInfo(mBarcode.rawValue);
                        mBarcode = null;
                    }
                }
            }
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
        DialogFactory.createCustomMaterialDialogInput(this, getString(R.string.barcode_input_tiltle), getString(R.string.barcode_input_message), getString(R.string.barcode_input_hint), "", getString(R.string.Ok), new MaterialDialog.InputCallback() {
            @Override
            public void onInput(@NonNull MaterialDialog dialog, CharSequence input) {
                //could capture and validate on key press
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

    @Override
    public void showHelperText() {
        DialogFactory.createOkMaterialDialog(this, getString(R.string.helper_text), new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                mPresenter.OnHelperTextSeen();
            }
        }).show();
    }
}
