package com.example.test.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.test.R;
import com.example.test.ui_models.PromoDiscountModel;

import java.util.List;

public class PromoListAdapter extends RecyclerView.Adapter<PromoListAdapter.PromoViewHolder> {
    public interface PromoItemClick {
        void onItemClicked(PromoDiscountModel model);
    }

    private PromoItemClick listener;
    private List<PromoDiscountModel> promoList;

    public PromoListAdapter(List<PromoDiscountModel> promoList, PromoItemClick listener) {
       this.promoList = promoList;
       this.listener = listener;
    }
    @NonNull
    @Override
    public PromoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View promoView = inflater.inflate(R.layout.item_promo_discount, parent, false);
        return new PromoViewHolder(promoView);
    }

    @Override
    public void onBindViewHolder(@NonNull PromoViewHolder holder, int position) {
        int index = holder.getAdapterPosition();
        //ToDo: Glide/Piccasso Library can be used to render server image urls
        holder.mImageView.setImageResource(promoList.get(position).getImageBannerRes());

        holder.mImageView.setOnClickListener(v -> listener.onItemClicked(promoList.get(position)));
    }

    @Override
    public int getItemCount() {
        return promoList.size();
    }

    class PromoViewHolder extends RecyclerView.ViewHolder {
        ImageView mImageView;

        public PromoViewHolder(@NonNull View itemView) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.iv_image);
        }
    }
}
