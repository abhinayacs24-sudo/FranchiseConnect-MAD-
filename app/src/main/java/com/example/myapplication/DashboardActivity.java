package com.example.myapplication;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class DashboardActivity extends AppCompatActivity {

    private ImageView ivAdCarousel, btnNavFavorites, btnNavProfile;
    private TextView tvWelcomeDashboard;
    private LinearLayout layoutCategories;
    private RecyclerView rvBrands;
    private GridLayout gridSimilarSites;
    private Button btnDashUploadBrand;
    private View btnNavLogout;

    private int currentAdIndex = 0;
    private int[] adImages = {
        R.drawable.ad1, 
        R.drawable.ad2, 
        R.drawable.ad3,
        R.drawable.ad4,
        R.drawable.ad5
    };
    private String[] adUrls = {
        "https://franchising.bk.com/franchise-fee",
        "https://www.vrlgroup.in/vrl_investors_desk.aspx",
        "https://franchise.tealogy.in/",
        "https://www.lakmesalon.in/",
        "https://www.franchiseindia.com/brands/t-r-autoworks.104371"
    };

    private String[][] similarSites = {
        {"Entrepreneur", "https://www.entrepreneur.com"},
        {"Indian Retailer", "https://www.indianretailer.com"},
        {"Restaurant India", "https://www.restaurantindia.in/"},
        {"Franchise Nepal", "https://www.franchisenepal.com/"},
        {"Franchise BD", "https://www.franchisebangladesh.com/"},
        {"Brand License", "https://www.indianretailer.com/brandlicense/"},
        {"BusinessEx", "https://www.businessex.com/"},
        {"Bradford", "https://www.bradfordlicenseindia.com/"},
        {"Francorp", "https://www.francorp.in/"},
        {"FranGlobal", "https://www.franglobal.com/"},
        {"Gaurav Marya", "https://www.gauravmarya.com/"},
        {"MSME", "https://www.msme.in/"}
    };

    private int[] similarSiteImages = {
        R.drawable.sim1, R.drawable.sim2, R.drawable.sim3, R.drawable.sim4,
        R.drawable.sim5, R.drawable.sim6, R.drawable.sim7, R.drawable.sim8,
        R.drawable.sim9, R.drawable.sim10, R.drawable.sim11, R.drawable.sim12
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        // Initialize Views
        ivAdCarousel = findViewById(R.id.ivAdCarousel);
        tvWelcomeDashboard = findViewById(R.id.tvWelcomeDashboard);
        layoutCategories = findViewById(R.id.layoutCategories);
        rvBrands = findViewById(R.id.rvBrands);
        gridSimilarSites = findViewById(R.id.gridSimilarSites);
        btnDashUploadBrand = findViewById(R.id.btnDashUploadBrand);
        btnNavFavorites = findViewById(R.id.btnNavFavorites);
        btnNavProfile = findViewById(R.id.btnNavProfile);
        btnNavLogout = findViewById(R.id.btnNavLogout);

        setupCategories();
        setupAdCarousel();
        setupSimilarSites();
        setupBrands();

        // Navigation
        btnNavProfile.setOnClickListener(v -> startActivity(new Intent(this, ProfileActivity.class)));
        btnNavFavorites.setOnClickListener(v -> Toast.makeText(this, "Favorites clicked", Toast.LENGTH_SHORT).show());
        btnDashUploadBrand.setOnClickListener(v -> startActivity(new Intent(this, UploadBrandActivity.class)));
        
        btnNavLogout.setOnClickListener(v -> {
            Intent intent = new Intent(this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        });

        findViewById(R.id.btnAdNext).setOnClickListener(v -> nextAd());
        findViewById(R.id.btnAdPrev).setOnClickListener(v -> prevAd());
        
        ivAdCarousel.setImageResource(adImages[0]);
        tvWelcomeDashboard.setText("Welcome, User!");
    }

    private void setupBrands() {
        List<Brand> brandList = new ArrayList<>();
        // Using sim images as logos for brands
        brandList.add(new Brand("Burger King", "Food & Beverages", "₹1.5Cr - 3Cr", R.drawable.ad1));
        brandList.add(new Brand("Lakme Salon", "Beauty & Salon", "₹50L - 1Cr", R.drawable.ad4));
        brandList.add(new Brand("Tealogy", "Food & Beverages", "₹10L - 20L", R.drawable.ad3));
        brandList.add(new Brand("VRL Logistics", "Logistics", "₹20L - 50L", R.drawable.ad2));
        brandList.add(new Brand("T-R Autoworks", "Automobiles", "₹15L - 30L", R.drawable.ad5));
        brandList.add(new Brand("MSME Center", "Education", "₹5L - 10L", R.drawable.sim12));

        BrandAdapter adapter = new BrandAdapter(brandList);
        rvBrands.setLayoutManager(new GridLayoutManager(this, 2));
        rvBrands.setAdapter(adapter);
    }

    private void setupCategories() {
        String[] categories = {
            "All Categories", "Food & Beverages", "Retail & Fashion", 
            "Health & Wellness", "Education", "Beauty & Salon", 
            "Automobiles", "Electronics", "Real Estate"
        };
        for (String cat : categories) {
            Button btn = new Button(this);
            btn.setText(cat);
            btn.setAllCaps(false);
            btn.setTextColor(getResources().getColor(android.R.color.white));
            btn.setBackgroundTintList(android.content.res.ColorStateList.valueOf(android.graphics.Color.parseColor("#1A60A5FA")));
            
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            );
            params.setMargins(8, 0, 8, 0);
            btn.setLayoutParams(params);
            
            btn.setOnClickListener(v -> Toast.makeText(this, "Filtering by: " + cat, Toast.LENGTH_SHORT).show());
            layoutCategories.addView(btn);
        }
    }

    private void setupAdCarousel() {
        ivAdCarousel.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(adUrls[currentAdIndex]));
            startActivity(intent);
        });
        
        final Handler handler = new Handler();
        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                nextAd();
                handler.postDelayed(this, 5000);
            }
        };
        handler.postDelayed(runnable, 5000);
    }

    private void nextAd() {
        currentAdIndex = (currentAdIndex + 1) % adImages.length;
        ivAdCarousel.setImageResource(adImages[currentAdIndex]);
    }

    private void prevAd() {
        currentAdIndex = (currentAdIndex - 1 + adImages.length) % adImages.length;
        ivAdCarousel.setImageResource(adImages[currentAdIndex]);
    }

    private void setupSimilarSites() {
        gridSimilarSites.removeAllViews();
        for (int i = 0; i < similarSites.length; i++) {
            final int index = i;
            ImageView iv = new ImageView(this);
            iv.setImageResource(similarSiteImages[i]);
            iv.setAdjustViewBounds(true);
            iv.setPadding(8, 8, 8, 8);
            
            GridLayout.LayoutParams params = new GridLayout.LayoutParams();
            params.width = 0;
            params.height = GridLayout.LayoutParams.WRAP_CONTENT;
            params.columnSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f);
            params.setMargins(4, 4, 4, 4);
            iv.setLayoutParams(params);

            iv.setOnClickListener(v -> {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(similarSites[index][1]));
                startActivity(intent);
            });
            gridSimilarSites.addView(iv);
        }
    }
}
