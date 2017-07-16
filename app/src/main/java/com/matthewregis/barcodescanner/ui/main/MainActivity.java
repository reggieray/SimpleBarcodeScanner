package com.matthewregis.barcodescanner.ui.main;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.google.android.gms.vision.barcode.Barcode;
import com.matthewregis.barcodescanner.R;
import com.matthewregis.barcodescanner.data.viewmodel.ItemViewModel;
import com.matthewregis.barcodescanner.ui.base.BaseActivity;
import com.matthewregis.barcodescanner.ui.item_info.ItemInfoActivity;
import com.matthewregis.barcodescanner.util.DialogFactory;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import devliving.online.mvbarcodereader.MVBarcodeScanner;
import timber.log.Timber;

public class MainActivity extends BaseActivity implements MainMvpView, View.OnClickListener {

    @Inject
    MainPresenter mPresenter;
    @Inject
    MainAdapter mAdapter;

    @MVBarcodeScanner.BarCodeFormat
    int[] mFormats = new int[]{Barcode.UPC_E, Barcode.UPC_A, Barcode.ISBN, Barcode.EAN_8, Barcode.EAN_13}; // Only use codes that upcitemdb.com support eg. UPC, ISBN or EAN
    final int REQ_CODE = 12;
    Barcode mBarcode;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.progess_bar)
    ProgressBar progressBar;
    @BindView(R.id.product_list)
    RecyclerView productList;
    @BindView(R.id.product_list_swipe_refresh)
    SwipeRefreshLayout productListSwipeRefresh;
    @BindView(R.id.fab2)
    FloatingActionButton fab2;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    @BindView(R.id.empty_list_text)
    AppCompatTextView emptyListText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityComponent().inject(this);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        productList.setAdapter(mAdapter);
        productList.setLayoutManager(new LinearLayoutManager(this));
        mPresenter.attachView(this);

        setSupportActionBar(toolbar);
        bindViews();
    }

    private void bindViews() {
        fab.setOnClickListener(this);
        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.OnRequestInput();
            }
        });
        productListSwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.LoadItems();
            }
        });
        mAdapter.setListener(new MainAdapterListener() {
            @Override
            public void onItemListEmpty() {
                mPresenter.OnItemListEmpty();
            }

            @Override
            public void onItemListPopulated() {
                mPresenter.OnItemListPopulated();
            }

            @Override
            public void onItemClick(ItemViewModel viewModel) {
                mPresenter.OnItemClick(viewModel);
            }
        });
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT | ItemTouchHelper.DOWN | ItemTouchHelper.UP) {

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
                //Remove swiped item from list and notify the RecyclerView
                final int position = viewHolder.getAdapterPosition();
                ItemViewModel item = mAdapter.getItemAtPos(position);
                mPresenter.RemoveItem(item);
            }
        });
        itemTouchHelper.attachToRecyclerView(productList);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.main_menu_delete_all:
                mPresenter.OnMenuDeleteAllSelected();
                return true;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
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
        mPresenter.LoadItems();
    }

    @Override
    public void onClick(View v) {
        mPresenter.OnRequestBarcodeScanner();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQ_CODE) {
            if (resultCode == RESULT_OK && data != null && data.getExtras() != null) {
                if (data.getExtras().containsKey(MVBarcodeScanner.BarcodeObject)) {
                    mBarcode = data.getParcelableExtra(MVBarcodeScanner.BarcodeObject);
                    if (mBarcode != null) {
                        Timber.d("Barcode result: %s", mBarcode.rawValue);
                        mPresenter.GetBarcodeInfo(mBarcode.rawValue);
                        mBarcode = null;
                    }
                }
            }
        }
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
    public void launchBarcodeScanner() {
        new MVBarcodeScanner.Builder()
                .setScanningMode(MVBarcodeScanner.ScanningMode.SINGLE_AUTO)
                .setFormats(mFormats)
                .build()
                .launchScanner(this, REQ_CODE);
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

    @Override
    public void setSwipeRefreshing(boolean value) {
        productListSwipeRefresh.setRefreshing(value);
    }

    @Override
    public void setItemList(List<ItemViewModel> items) {
        mAdapter.setItems(items);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void addItemList(List<ItemViewModel> items) {
        mAdapter.addItems(items);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void addItem(ItemViewModel item) {
        mAdapter.addItem(item);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void showProgressbar() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressbar() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void removeItem(ItemViewModel item) {
        mAdapter.removeItem(item);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void showEmptyListText() {
        emptyListText.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideEmptyListText() {
        emptyListText.setVisibility(View.GONE);
    }

    @Override
    public void showConfirmDeleteAllDialog() {
        DialogFactory.createConfirmMaterialDialog(this, getString(R.string.confirm_delete_all_items), new MaterialDialog.SingleButtonCallback() {
            @Override
            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                //Ok
                mPresenter.OnDeleteAllConfirmed();
                dialog.dismiss();
            }
        }, new MaterialDialog.SingleButtonCallback() {
            @Override
            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                //Cancel
                dialog.dismiss();
            }
        }).show();
    }

    @Override
    public void navigateToItemInfo() {
        startActivity(new Intent(this, ItemInfoActivity.class));
    }
}
