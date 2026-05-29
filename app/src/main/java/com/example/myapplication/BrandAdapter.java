package com.example.myapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import java.util.List;

public class BrandAdapter extends RecyclerView.Adapter<BrandAdapter.BrandViewHolder> {

    private List<Brand> brandList;
    private OnBrandClickListener listener;

    public interface OnBrandClickListener {
        void onBrandClick(Brand brand);
        void onFavoriteClick(Brand brand);
    }

    public BrandAdapter(List<Brand> brandList, OnBrandClickListener listener) {
        this.brandList = brandList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public BrandViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_brand, parent, false);
        return new BrandViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BrandViewHolder holder, int position) {
        Brand brand = brandList.get(position);
        holder.tvBrandName.setText(brand.getName());
        holder.tvBrandCategory.setText(brand.getCategory());
        holder.tvBrandInvestment.setText("Investment: " + brand.getInvestment());

        int logoRes = brand.getLogoResId() != 0 ? brand.getLogoResId() : R.drawable.logo;
        String logoUrl = RetrofitClient.getAbsoluteUrl(brand.getLogoUrl());
        if (logoUrl != null && !logoUrl.isEmpty()) {
            Glide.with(holder.itemView.getContext())
                    .load(logoUrl)
                    .placeholder(logoRes)
                    .error(logoRes)
                    .into(holder.ivBrandLogo);
        } else {
            holder.ivBrandLogo.setImageResource(logoRes);
        }

        // Set click listener on the entire card/itemView for premium UX
        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onBrandClick(brand);
            }
        });

        holder.btnViewDetails.setOnClickListener(v -> {
            if (listener != null) {
                listener.onBrandClick(brand);
            }
        });

        holder.btnAddToFav.setOnClickListener(v -> {
            if (listener != null) {
                listener.onFavoriteClick(brand);
            }
        });
    }

    public void updateList(List<Brand> newList) {
        this.brandList = newList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return brandList.size();
    }

    public static class BrandViewHolder extends RecyclerView.ViewHolder {
        ImageView ivBrandLogo, btnAddToFav;
        TextView tvBrandName, tvBrandCategory, tvBrandInvestment;
        Button btnViewDetails;

        public BrandViewHolder(@NonNull View itemView) {
            super(itemView);
            ivBrandLogo = itemView.findViewById(R.id.ivBrandLogo);
            btnAddToFav = itemView.findViewById(R.id.btnAddToFav);
            tvBrandName = itemView.findViewById(R.id.tvBrandName);
            tvBrandCategory = itemView.findViewById(R.id.tvBrandCategory);
            tvBrandInvestment = itemView.findViewById(R.id.tvBrandInvestment);
            btnViewDetails = itemView.findViewById(R.id.btnViewDetails);
        }
    }
}
