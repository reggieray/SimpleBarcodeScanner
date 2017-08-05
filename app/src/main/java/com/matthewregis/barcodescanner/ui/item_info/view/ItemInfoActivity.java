package com.matthewregis.barcodescanner.ui.item_info.view;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import com.matthewregis.barcodescanner.R;
import com.matthewregis.barcodescanner.databinding.ActivityItemInfoBinding;
import com.matthewregis.barcodescanner.ui.base.BaseActivity;
import com.matthewregis.barcodescanner.ui.item_info.viewmodel.ItemInfoViewModel;

import javax.inject.Inject;


public class ItemInfoActivity extends BaseActivity {

    @Inject
    ItemInfoViewModel itemInfoViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityComponent().inject(this);
        ActivityItemInfoBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_item_info);
        binding.setItemViewModel(itemInfoViewModel);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setHomeButtonEnabled(true);
        itemInfoViewModel.onCreate();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        itemInfoViewModel.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        itemInfoViewModel.onResume();
    }

}
