package com.example.myapplication;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.bumptech.glide.Glide;

public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        // Adjust for Edge-to-Edge Status Bar Insets (using a safe margin/padding setup)
        android.view.View root = findViewById(R.id.btnBack);
        
        // Bind Views
        ImageView ivDetailLogo = findViewById(R.id.ivDetailLogo);
        TextView tvDetailName = findViewById(R.id.tvDetailName);
        TextView tvDetailCategory = findViewById(R.id.tvDetailCategory);
        TextView tvDetailCategoryOverlay = findViewById(R.id.tvDetailCategoryOverlay);
        TextView tvDetailInvestment = findViewById(R.id.tvDetailInvestment);
        ImageView btnBack = findViewById(R.id.btnBack);
        Button btnApplyNow = findViewById(R.id.btnApplyNow);

        // Get Data from Intent
        String name = getIntent().getStringExtra("brandName");
        String category = getIntent().getStringExtra("brandCategory");
        String investment = getIntent().getStringExtra("brandInvestment");
        String logoUrl = getIntent().getStringExtra("brandLogoUrl");

        // Set content
        if (name != null) tvDetailName.setText(name);
        if (category != null) {
            tvDetailCategory.setText(category);
            if (tvDetailCategoryOverlay != null) {
                tvDetailCategoryOverlay.setText(category);
            }
        }
        if (investment != null) tvDetailInvestment.setText(investment);

        // Load image using Glide with dynamic host resolution
        int logoRes = R.drawable.logo;
        String absoluteLogoUrl = RetrofitClient.getAbsoluteUrl(logoUrl);
        if (absoluteLogoUrl != null && !absoluteLogoUrl.isEmpty()) {
            Glide.with(this)
                    .load(absoluteLogoUrl)
                    .placeholder(logoRes)
                    .error(logoRes)
                    .into(ivDetailLogo);
        } else {
            ivDetailLogo.setImageResource(logoRes);
        }

        // Set Click Listeners
        if (btnBack != null) {
            btnBack.setOnClickListener(v -> finish());
        }
        
        if (btnApplyNow != null) {
            btnApplyNow.setOnClickListener(v -> 
                Toast.makeText(SecondActivity.this, "Inquiry sent successfully for " + (name != null ? name : "brand") + "!", Toast.LENGTH_LONG).show()
            );
        }
    }
}
