package com.matthewregis.barcodescanner.ui.main;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.matthewregis.barcodescanner.R;
import com.matthewregis.barcodescanner.data.viewmodel.ItemViewModel;
import com.matthewregis.barcodescanner.injection.ApplicationContext;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by matthew on 07/07/2017.
 */

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.MainViewHolder> {

    private Context mContext;
    private List<ItemViewModel> mItems;
    private MainAdapterListener mListener;

    @Inject
    public MainAdapter(@ApplicationContext Context context) {
        mContext = context;
        mItems = new ArrayList<>();
    }

    public void setListener(MainAdapterListener listener) {
        this.mListener = listener;
    }

    public void setItems(List<ItemViewModel> items) {
        this.mItems = items;
        if (this.mItems.isEmpty()) {
            mListener.onItemListEmpty();
        } else {
            mListener.onItemListPopulated();
        }
    }

    public void addItem(ItemViewModel item) {
        this.mItems.add(0, item);
        mListener.onItemListPopulated();
    }

    public void addItems(List<ItemViewModel> items) {
        this.mItems.addAll(items);
        if (!this.mItems.isEmpty()) {
            mListener.onItemListPopulated();
        }
    }

    public void removeItem(ItemViewModel item) {
        notifyItemRemoved(mItems.indexOf(item));
        mItems.remove(item);
        if (mItems.isEmpty()) {
            mListener.onItemListEmpty();
        }
    }

    @Override
    public MainViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_detail_row, parent, false);
        return new MainViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MainViewHolder holder, int position) {
        final ItemViewModel item = mItems.get(position);
        holder.title.setText(item.title());
        holder.brand.setText(item.brand());
        holder.asin.setText(item.asin());
        holder.date.setText(item.datetime());
        holder.image.setImageResource(R.drawable.no_image_available);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onItemClick(item);
            }
        });
        if (item.imageurl().isEmpty()) {
            return;
        }

        Glide.with(mContext)
                .load(item.imageurl())
                .fitCenter()
                .placeholder(R.drawable.no_image_available)
                .into(holder.image);
    }

    public ItemViewModel getItemAtPos(int pos) {
        return mItems.get(pos);
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    class MainViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.image)
        ImageView image;
        @BindView(R.id.title)
        AppCompatTextView title;
        @BindView(R.id.brand)
        AppCompatTextView brand;
        @BindView(R.id.asin)
        AppCompatTextView asin;
        @BindView(R.id.date)
        AppCompatTextView date;
        @BindView(R.id.card_view)
        CardView cardView;

        public MainViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
