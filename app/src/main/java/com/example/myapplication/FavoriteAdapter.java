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

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder> {

    private List<Brand> favoriteList;
    private OnFavoriteActionListener actionListener;

    public interface OnFavoriteActionListener {
        void onRemove(int position);
        void onViewDetails(Brand brand);
    }

    public FavoriteAdapter(List<Brand> favoriteList, OnFavoriteActionListener actionListener) {
        this.favoriteList = favoriteList;
        this.actionListener = actionListener;
    }

    @NonNull
    @Override
    public FavoriteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_favorite, parent, false);
        return new FavoriteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteViewHolder holder, int position) {
        Brand brand = favoriteList.get(position);
        holder.tvFavName.setText(brand.getName());
        holder.tvFavCategory.setText(brand.getCategory());
        holder.tvFavInvestment.setText("Investment: " + brand.getInvestment());

        int logoRes = brand.getLogoResId() != 0 ? brand.getLogoResId() : R.drawable.logo;
        String logoUrl = RetrofitClient.getAbsoluteUrl(brand.getLogoUrl());
        if (logoUrl != null && !logoUrl.isEmpty()) {
             Glide.with(holder.itemView.getContext())
                      .load(logoUrl)
                      .placeholder(logoRes)
                      .error(logoRes)
                      .into(holder.ivFavLogo);
         } else {
             holder.ivFavLogo.setImageResource(logoRes);
         }

        // Make entire card clickable for premium UX
        holder.itemView.setOnClickListener(v -> {
            if (actionListener != null) {
                actionListener.onViewDetails(brand);
            }
        });

        holder.btnRemoveFavorite.setOnClickListener(v -> {
            if (actionListener != null) {
                actionListener.onRemove(position);
            }
        });

        holder.btnViewDetails.setOnClickListener(v -> {
            if (actionListener != null) {
                actionListener.onViewDetails(brand);
            }
        });
    }

    @Override
    public int getItemCount() {
        return favoriteList.size();
    }

    public static class FavoriteViewHolder extends RecyclerView.ViewHolder {
        ImageView ivFavLogo;
        TextView tvFavName, tvFavCategory, tvFavInvestment;
        Button btnViewDetails, btnRemoveFavorite;

        public FavoriteViewHolder(@NonNull View itemView) {
            super(itemView);
            ivFavLogo = itemView.findViewById(R.id.ivFavLogo);
            tvFavName = itemView.findViewById(R.id.tvFavName);
            tvFavCategory = itemView.findViewById(R.id.tvFavCategory);
            tvFavInvestment = itemView.findViewById(R.id.tvFavInvestment);
            btnViewDetails = itemView.findViewById(R.id.btnViewDetails);
            btnRemoveFavorite = itemView.findViewById(R.id.btnRemoveFavorite);
        }
    }
}
